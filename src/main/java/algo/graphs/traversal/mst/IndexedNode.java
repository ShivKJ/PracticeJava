package algo.graphs.traversal.mst;

import algo.graphs.traversal.VertexTraversalCode;

public class IndexedNode {
	public static final int		DEFAULT_INDEX	= -1;
	private int					index;
	private VertexTraversalCode	code;

	public IndexedNode() {
		this.index = DEFAULT_INDEX;
	}

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
