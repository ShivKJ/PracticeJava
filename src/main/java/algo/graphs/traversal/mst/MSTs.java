package algo.graphs.traversal.mst;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.traversal.TraversalVertex;
import algo.graphs.traversal.VertexTraversalCode;

public final class MSTs {

	private MSTs() {}

	public static <V extends TraversalVertex<E>, E, W extends Edge<? extends V>> Graph<V, W> kruskal(Graph<V, W> graph) {

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

	private final static class Parent<T> extends TraversalVertex<T> {
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

	//------------------------------------------------------------------------------------------
	public static <V extends TraversalVertex<E>, E, W extends Edge<? extends V>> Graph<V, W> prim(Graph<V, W> graph, V src) {
		return null;
	}

	final static class DistanceTracker<T> extends TraversalVertex<T> {
		TraversalVertex<T>	vertex;
		int					distance;

		public DistanceTracker(TraversalVertex<T> vertex) {
			this.vertex = vertex;
			this.distance = Integer.MAX_VALUE;
		}

		public int getDistance() {
			return distance;
		}

		public void setDistance(int distance) {
			this.distance = distance;
		}

		@Override
		public int uid() {

			return vertex.uid();
		}

		@Override
		public T getData() {

			return vertex.getData();
		}

		@Override
		public void setStatusCode(VertexTraversalCode statusCode) {
			vertex.setStatusCode(statusCode);
		}

		@Override
		public VertexTraversalCode statusCode() {
			return vertex.statusCode();
		}

		@Override
		public TraversalVertex<T> parent() {

			return vertex.parent();
		}

		@Override
		public void setParent(TraversalVertex<T> parent) {
			vertex.setParent(parent);
		}

	}
}
