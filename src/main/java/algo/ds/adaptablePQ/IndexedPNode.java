package algo.ds.adaptablePQ;

import algo.graphs.traversal.VertexTraversalCode;

public abstract class IndexedPNode<E, P extends Comparable<P>> implements PNode<E, P> {
	public static final int		DEFAULT_INDEX	= -1;
	private int					index;
	private VertexTraversalCode	code;

	public IndexedPNode() {
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
