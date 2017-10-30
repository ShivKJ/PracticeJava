package algo.graphs.traversal.mst;

import algo.graphs.traversal.VertexTraversalCode;

public class IndexedNode {

	private int					index;
	private VertexTraversalCode	code;

	public int index() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public VertexTraversalCode code() {
		return code;
	}

	public void setCode(VertexTraversalCode code) {
		this.code = code;
	}
}
