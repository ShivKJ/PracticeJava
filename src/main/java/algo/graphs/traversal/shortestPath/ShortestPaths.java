package algo.graphs.traversal.shortestPath;

import static algo.graphs.traversal.Utils.traversalVertexToPQNode;
import static java.lang.Double.MAX_VALUE;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import algo.ds.adaptablePQ.AdaptablePriorityQueue;
import algo.ds.adaptablePQ.ArrayPriorityQueue;
import algo.ds.adaptablePQ.IndexedPNode;
import algo.graphs.Graph;
import algo.graphs.traversal.TraversalVertex;

public final class ShortestPaths {
	private ShortestPaths() {}

	public final static <PQ extends IndexedPNode<? extends TraversalVertex, Double>, T, P extends Comparable<P>> void relax(PQ u, PQ v, double w) {
		Double uData = u.getPriority() , vData = v.getPriority();

		if (vData > uData + w) {
			v.setPriority(uData + w);
			v.getData().setParent(u.getData());
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends TraversalVertex> List<T> dijkstra(Graph<T, ?> graph, T src, T dst) {
		Map<T, IndexedPNode<T, Double>> vertexToPQNode = traversalVertexToPQNode(graph.vertices(), MAX_VALUE);
		IndexedPNode<T, Double> srcNode = vertexToPQNode.get(src);

		src.setParent(null);
		srcNode.setPriority(0.);

		AdaptablePriorityQueue<IndexedPNode<T, Double>> pq = new ArrayPriorityQueue<>(vertexToPQNode.values());
		boolean reachableToDst = false;

		do {
			IndexedPNode<T, Double> uNode = pq.poll();
			T u = uNode.getData();

			for (T v : graph.adjacentVertices(u)) {
				relax(uNode, vertexToPQNode.get(v), graph.distance(u, v));
				if (v == dst)
					reachableToDst = true;
			}

		} while (!pq.isEmpty());

		if (reachableToDst) {
			List<T> path = new ArrayList<>();

			do
				path.add(dst);
			while ((dst = (T) dst.parent()) != null);

			return path;
		} else
			return emptyList();
	}
}
