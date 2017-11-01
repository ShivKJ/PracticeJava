package algo.graphs.traversal.shortestPath;

import algo.graphs.traversal.TraversalVertex;

public final class ShortestPaths {
	private ShortestPaths() {}

	public final static <E extends TraversalVertex<D>, T, D> void relax(E u, E v, double w) {
		Double uData = u.getvData() , vData = v.getvData();
		if (vData > uData + w) {
			v.setvData(uData + w);
			v.setParent(u);
		}
	}
	
}
