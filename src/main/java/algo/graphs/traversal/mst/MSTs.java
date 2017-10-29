package algo.graphs.traversal.mst;

import static algo.graphs.Graphs.emptyGraph;
import static java.lang.Double.MAX_VALUE;
import static java.util.Collections.sort;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.traversal.TraversalVertex;

public final class MSTs {

	private MSTs() {}

	public static <V extends TraversalVertex<E>, E, W extends Edge<? extends V>> Graph<V, W> kruskal(Graph<V, W> graph) {

		List<W> mst = new ArrayList<>();

		Collection<V> vertices = graph.getVertices();

		vertices.forEach(Parent<E>::new);

		List<W> edges = new ArrayList<>(graph.edges());

		sort(edges);

		for (W w : edges) {
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
	@SuppressWarnings("unchecked")
	public static <V extends TraversalVertex<E>, E, W extends Edge<? extends V>> Graph<V, W> prim(Graph<V, W> graph) {

		Collection<V> vertices = graph.getVertices();

		if (vertices.isEmpty())
			return emptyGraph();

		Map<V, PQNode<Double>> vToPQNode = graph.getVertices()
				.stream()
				.collect(toMap(identity(), e -> new PQNode<>(e, MAX_VALUE)));

		PQNode<Double>[] vs = vToPQNode.values().toArray(new PQNode[vToPQNode.size()]);

		PQNode<Double> src = vs[0];

		src.setPriority(0.);
		src.getVertex().setParent(null);

		AdaptablePriorityQueue<PQNode<Double>> priorityQueue = new ArrayPriorityQueue<>(vs);

		Collection<W> edges = new ArrayList<>();

		while (!priorityQueue.isEmpty()) {
			PQNode<Double> uNode = priorityQueue.poll();

			V u = (V) uNode.getVertex();

			if (u.parent() != null)
				edges.add(graph.edge((V) u.parent(), u).get());

			for (V v : graph.adjacentVertices((V) uNode.getVertex())) {
				Optional<W> edge = graph.edge(u, v);

				if (edge.isPresent()) {
					PQNode<Double> vNode = vToPQNode.get(v);

					if (priorityQueue.contains(vNode)) {
						Double cost = edge.get().distance();

						if (cost < vNode.getPriority()) {
							v.setParent(u);
							priorityQueue.updatePriority(vNode, cost);
						}
					}
				}
			}
		}

		return new MSTGraph<>(vertices, edges);
	}

}
