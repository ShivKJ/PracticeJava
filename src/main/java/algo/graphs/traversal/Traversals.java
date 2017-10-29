package algo.graphs.traversal;

import static algo.graphs.traversal.VertexTraversalCode.DONE;
import static algo.graphs.traversal.VertexTraversalCode.IN_PROGRESS;
import static algo.graphs.traversal.VertexTraversalCode.NEW;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.Consumer;

import algo.graphs.Edge;
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
	//-------------------------------------------- DFS --------------------------------------------------

	public static <V extends TraversalVertex<E>, E> void dfs(Graph<V, ?> graph, V srcVrtx,
			Consumer<? super V> preProcessing,
			Consumer<? super V> postProcessing) {

		if (!isNew(srcVrtx))
			return;

		preProcessing.accept(srcVrtx);
		srcVrtx.setStatusCode(IN_PROGRESS);

		for (V v : graph.adjacentVertices(srcVrtx))
			if (isNew(v)) {
				v.setParent(srcVrtx);
				dfs(graph, v, preProcessing, postProcessing);
			}

		postProcessing.accept(srcVrtx);
		srcVrtx.setStatusCode(DONE);
	}

	//-------------------------------------------- MST KRUSKAL ---------------------------------------------

	public static <V extends TraversalVertex<E>, E, W extends Edge<? extends V>> Graph<V, W> mstKruskal(Graph<V, W> graph) {
		List<W> mst = new ArrayList<>();
		Collection<V> vertices = graph.getVertices();

		vertices.forEach(Parent<E>::new);

		Queue<W> priorityQueue = new PriorityQueue<>(graph.edges());

		while (!priorityQueue.isEmpty()) {
			W w = priorityQueue.poll();

			Parent<E> pu = (Parent<E>) w.getSrc().parent() , pv = (Parent<E>) w.getDst().parent();

			if (pu != pv) {
				mst.add(w);
				pu.merge(pv);
			}
		}

		return new MSTGraph<>(vertices, mst);
	}

	private static class Parent<T> extends TraversalVertex<T> {
		private final Collection<TraversalVertex<T>> vertices;

		Parent(TraversalVertex<T> v) {
			this.vertices = new LinkedList<>();
			add(v);
		}

		void merge(Parent<T> pv) {
			pv.vertices.forEach(this::add);
		}

		void add(TraversalVertex<T> v) {
			vertices.add(v);
			v.setParent(this);

		}

		@Override
		public T getData() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int uid() {
			throw new UnsupportedOperationException();

		}

	}

	//-------------------------------------------- MST PRIM --------------------------------------------------

	public static <V extends TraversalVertex<E>, E, W extends Edge<? extends V>> Graph<V, W> mstPrim(Graph<V, W> graph) {
		
		return null;
	}
}
