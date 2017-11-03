package algo.graphs.traversal.mst;

import static algo.graphs.Graphs.emptyGraph;
import static algo.graphs.traversal.Utils.traversalVertexToPQNode;
import static java.lang.Double.MAX_VALUE;
import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import algo.ds.adaptablePQ.AdaptablePriorityQueue;
import algo.ds.adaptablePQ.ArrayPriorityQueue;
import algo.ds.adaptablePQ.IndexedPNode;
import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.traversal.TraversalVertex;

public final class MSTs {

	private MSTs() {}

	public static <V extends TraversalVertex, W extends Edge<? extends V>> Graph<V, W> kruskal(Graph<V, W> graph) {

		List<W> mst = new ArrayList<>();

		Collection<V> vertices = graph.vertices();

		vertices.forEach(Parent::new);

		List<W> edges = new ArrayList<>(graph.edges());

		sort(edges);

		for (W w : edges) {
			Parent pU = (Parent) w.getSrc().parent() , pV = (Parent) w.getDst().parent();

			if (pU != pV) {
				mst.add(w);
				pU.merge(pV);
			}
		}

		return new MSTGraph<>(vertices, mst);
	}

	private final static class Parent extends TraversalVertex {
		private final Collection<TraversalVertex> vertices;

		Parent(TraversalVertex v) {
			this.vertices = new LinkedList<>();
			add(v);
		}

		void merge(Parent pv) {
			pv.vertices.forEach(this::add);
		}

		void add(TraversalVertex v) {
			vertices.add(v);
			v.setParent(this);

		}

		@Override
		public int uid() {
			throw new UnsupportedOperationException();
		}

	}

	@SuppressWarnings("unchecked")
	public static <V extends TraversalVertex, W extends Edge<? extends V>> Graph<V, W> prim(Graph<V, W> graph) {

		Collection<V> vertices = graph.vertices();

		if (vertices.isEmpty())
			return emptyGraph();;

		Map<V, IndexedPNode<V, Double>> vToPQNode = traversalVertexToPQNode(graph.vertices(), MAX_VALUE);

		List<IndexedPNode<V, Double>> vs = new ArrayList<>(vToPQNode.values());

		IndexedPNode<V, Double> src = vs.get(0);

		src.setPriority(0.);
		src.getData().setParent(null);

		AdaptablePriorityQueue<IndexedPNode<V, Double>> priorityQueue = new ArrayPriorityQueue<>(vs, Double::compareTo);

		Collection<W> edges = new ArrayList<>();

		while (!priorityQueue.isEmpty()) {
			IndexedPNode<V, Double> uNode = priorityQueue.poll();

			V u = uNode.getData();

			if (u.parent() != null)
				edges.add(graph.edge((V) u.parent(), u).get());

			for (V v : graph.adjacentVertices(u)) {
				IndexedPNode<V, Double> vNode = vToPQNode.get(v);

				if (priorityQueue.contains(vNode)) {
					Double cost = graph.distance(u, v);

					if (cost.compareTo(vNode.getPriority()) < 0) {
						v.setParent(u);
						priorityQueue.updatePriority(vNode, cost);
					}
				}
			}
		}

		return new MSTGraph<>(vertices, edges);
	}

}
