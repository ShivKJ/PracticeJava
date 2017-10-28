package algo.graphs.traversal;

import static algo.graphs.traversal.StatusCode.DONE;
import static algo.graphs.traversal.StatusCode.IN_PROGRESS;
import static algo.graphs.traversal.StatusCode.NEW;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public final class Traversals {
	public Traversals() {}

	//-----------------------------------------  BFS -----------------------------------------------------

	public static final <E> void bfs(TraversalVertex<E> srcVrtx,
			Consumer<? super TraversalVertex<E>> preProcessor,
			Consumer<? super TraversalVertex<E>> postProcessor) {
		// Only those vertices will be processed which have status code "NEW". 
		if (!isNew(srcVrtx))
			return;

		srcVrtx.setParent(null);

		Queue<TraversalVertex<E>> queue = new BFSQueue<>(preProcessor, postProcessor);

		queue.add(srcVrtx);

		while (!queue.isEmpty()) {
			TraversalVertex<E> curr = queue.poll();

			for (TraversalVertex<E> v : curr.adjacentVertices())
				if (isNew(v)) {
					v.setParent(curr);
					queue.add(v);
				}

		}

	}

	public static final <T extends TraversalVertex<E>, E> void bfs(T srcVrtx) {
		bfs(srcVrtx, e -> {}, e -> {});
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

	public static <E> void dfs(TraversalVertex<E> srcVrtx,
			Consumer<? super TraversalVertex<E>> preProcessing,
			Consumer<? super TraversalVertex<E>> postProcessing) {

		if (!isNew(srcVrtx))
			return;

		preProcessing.accept(srcVrtx);
		srcVrtx.setStatusCode(IN_PROGRESS);

		for (TraversalVertex<E> v : srcVrtx.adjacentVertices())
			if (isNew(v)) {
				v.setParent(srcVrtx);
				dfs(v, preProcessing, postProcessing);
			}

		postProcessing.accept(srcVrtx);
		srcVrtx.setStatusCode(DONE);
	}
}
