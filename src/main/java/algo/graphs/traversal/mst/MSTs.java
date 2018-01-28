package algo.graphs.traversal.mst;

import static algo.graphs.traversal.Utils.traversalVertexToPQNode;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;
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

/**
 * Minimum spanning tree(MST) is a tree, such that sum of edges present is minimum,
 * and if two vertices were connected earlier then they remain connected.
 *  
 * @author shiv
 *
 */
public final class MSTs {

	private MSTs() {}

	/**
	 * This methods finds minimum spanning tree(MST) using Kruskal algorithm.
	 * This is achieved via greedy method.
	 * 
	 * First we sort edges in increasing order of edge-weight.
	 * Then for each edge which connects two vertices having different parent, merging children of parents.
	 * 
	 * @param graph
	 * @return
	 */

	public static <V extends TraversalVertex, W extends Edge<? extends V>> Graph<V, W> kruskal(Graph<V, W> graph) {

		List<W> mstEdges = new ArrayList<>();

		Collection<V> vertices = graph.vertices();

		vertices.forEach(Parent::new);// creating parent for each vertex.

		List<W> edges = new ArrayList<>(graph.edges());

		sort(edges);

		for (W w : edges) {
			Parent pU = (Parent) w.getSrc().parent() , pV = (Parent) w.getDst().parent();

			if (pU != pV) {
				mstEdges.add(w);
				pU.merge(pV);// merging all children of pV to pU and setting parent to pU. 
			}
		}

		return new MSTGraph<>(vertices, mstEdges);
	}

	private final static class Parent extends TraversalVertex {
		private Collection<TraversalVertex> vertices;

		Parent(TraversalVertex v) {
			this.vertices = new LinkedList<>();
			add(v);
		}

		void merge(Parent pv) {
			pv.vertices.forEach(this::add);
			pv.vertices = null;
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

	/**
	 * Implementing MST using Prim algorithm.
	 * This uses Adaptable priority queue. 
	 * 
	 * We start with a vertex. Setting its patent to null.
	 * Now we poll vertex from priority queue and update adjacent 
	 * vertices if they in priority queue and has high distance stored.
	 * 
	 *  
	 * @param graph
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static <V extends TraversalVertex, W extends Edge<? extends V>> Graph<V, W> prim(Graph<V, W> graph) {

		Collection<V> vertices = graph.vertices();

		Map<V, IndexedPNode<V, Double>> vToPQNode = traversalVertexToPQNode(graph.vertices(), POSITIVE_INFINITY);

		List<IndexedPNode<V, Double>> vs = new ArrayList<>(vToPQNode.values());

		IndexedPNode<V, Double> src = vs.get(0);

		src.setPriority(NEGATIVE_INFINITY);
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
