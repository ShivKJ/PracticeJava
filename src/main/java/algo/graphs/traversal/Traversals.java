package algo.graphs.traversal;

import static algo.graphs.traversal.VertexTraversalCode.DONE;
import static algo.graphs.traversal.VertexTraversalCode.IN_PROGRESS;
import static algo.graphs.traversal.VertexTraversalCode.NEW;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import algo.graphs.Graph;

public final class Traversals {
	public Traversals() {}

	//-----------------------------------------  BFS -----------------------------------------------------
	/**
	 * This function implements breadth first search starting from a source vertex.
	 * 
	 * It is required that VertexTraversalCode for all vertices is set to NEW.
	 * else they will be avoided in traversal process.
	 * 
	 * Implementation uses Queue data structure(FIFO). 
	 * Key idea is to poll vertex from queue and enqueue adjacent unvisited vertices.
	 *   
	 * During enqueuing and dequeuing operation, the vertex can be processed via 
	 * Consumer preProcessor and postProcessor. 
	 * VertexTraversalCode is updated during these operation accordingly.
	 * 
	 * 
	 * @param graph
	 * @param srcVrtx
	 * @param preProcessor
	 * @param postProcessor
	 */

	public static final <T extends TraversalVertex> void bfs(Graph<T, ?> graph, T srcVrtx,
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
					v.setParent(curr);// setting v parent to curr for tracing.
					queue.add(v);
				}
		}
	}

	public static final <T extends TraversalVertex> void bfs(Graph<T, ?> graph, T srcVrtx) {
		bfs(graph, srcVrtx, t -> {}, t -> {});
	}

	private static final class BFSQueue<T extends TraversalVertex> extends LinkedList<T> {

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
			e.setCode(IN_PROGRESS);

			return super.add(e);
		}

		@Override
		public T poll() {
			T polled = super.poll();

			postProcessor.accept(polled);
			polled.setCode(DONE);

			return polled;
		}
	}

	//---------------------------------------------------------------------------------------------------
	private static boolean isNew(TraversalVertex v) {
		return v.code() == NEW;
	}

	private static boolean isDone(TraversalVertex v) {
		return v.code() == DONE;
	}

	//-------------------------------------------- DFS --------------------------------------------------

	/**
	 * This function implements depth first search.
	 * 
	 * It is required that VertexTraversalCode for all vertices is set to NEW
	 * else they will be avoided in traversal process.
	 * 
	 * @param graph
	 * @param srcVrtx
	 * @param preProcessing
	 * @param postProcessing
	 */
	public static <V extends TraversalVertex> void dfs(Graph<V, ?> graph, V srcVrtx,
	    Consumer<? super V> preProcessing,
	    Consumer<? super V> postProcessing) {

		if (!isNew(srcVrtx))
			return;

		preProcessing.accept(srcVrtx);
		srcVrtx.setCode(IN_PROGRESS);

		for (V v : graph.adjacentVertices(srcVrtx))
			if (isNew(v)) {
				v.setParent(srcVrtx);
				dfs(graph, v, preProcessing, postProcessing);
			}

		postProcessing.accept(srcVrtx);
		srcVrtx.setCode(DONE);
	}

	public static <V extends TraversalVertex> boolean isConnected(Graph<V, ?> graph) {
		Collection<V> vs = graph.vertices();

		if (!vs.isEmpty()) {
			Iterator<V> iter = vs.iterator();
			bfs(graph, iter.next());

			while (iter.hasNext())
				if (!isDone(iter.next()))
					return false;
		}

		return true;
	}
}
