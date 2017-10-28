package algo.graphs.traversal;

import static algo.graphs.traversal.VertexTraversalCode.DONE;
import static algo.graphs.traversal.VertexTraversalCode.IN_PROGRESS;
import static algo.graphs.traversal.VertexTraversalCode.NEW;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import algo.graphs.Graph;

public final class Traversals {
	public Traversals() {}

	//-----------------------------------------  BFS -----------------------------------------------------

	public static final <T extends TraversalVertex<E>, E> void bfs(Graph<T, ?> graph, T srcVrtx,
			Consumer<? super T> preProcessor,
			Consumer<? super T> postProcessor) {
		// Only those vertices will be processed which have status code "NEW". 
		if (!isNew(srcVrtx))
			return;

		srcVrtx.setParent(null);

		Queue<T> queue = new BFSQueue<>(preProcessor, postProcessor);

		queue.add(srcVrtx);

		while (!queue.isEmpty()) {
			T curr = queue.poll();

			for (T v : graph.adjacentVertices(curr))
				if (isNew(v)) {
					v.setParent(curr);
					queue.add(v);
				}
		}
	}

	private static final class BFSQueue<T extends TraversalVertex<?>> extends LinkedList<T> {

		private static final long	serialVersionUID	= 1L;
		private final Consumer<T>	preProcessor , postProcessor;

		@SuppressWarnings("unchecked")
		public BFSQueue(Consumer<? super T> preProcessor, Consumer<? super T> postProcessor) {
			this.preProcessor = (Consumer<T>) preProcessor;
			this.postProcessor = (Consumer<T>) postProcessor;
		}

		@Override
		public boolean add(T e) {
			preProcessor.accept(e);
			e.setStatusCode(IN_PROGRESS);

			return super.add(e);
		}

		@Override
		public T poll() {
			T polled = super.poll();

			postProcessor.accept(polled);
			polled.setStatusCode(DONE);

			return polled;
		}
	}

	//---------------------------------------------------------------------------------------------------
	private static <T> boolean isNew(TraversalVertex<T> v) {
		return v.statusCode() == NEW;
	}

	public static <T extends TraversalVertex<E>, E> void dfs(Graph<T, ?> graph, T srcVrtx,
			Consumer<? super T> preProcessing,
			Consumer<? super T> postProcessing) {

		if (!isNew(srcVrtx))
			return;

		preProcessing.accept(srcVrtx);
		srcVrtx.setStatusCode(IN_PROGRESS);

		for (T v : graph.adjacentVertices(srcVrtx))
			if (isNew(v)) {
				v.setParent(srcVrtx);
				dfs(graph, v, preProcessing, postProcessing);
			}

		postProcessing.accept(srcVrtx);
		srcVrtx.setStatusCode(DONE);
	}
}
