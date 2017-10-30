package algo.graphs.traversal.mst;

import algo.graphs.traversal.VertexTraversalCode;

class PQNode<E, P extends Comparable<P>> {
	private int					index;
	private final E				e;
	private P					p;
	private VertexTraversalCode	code;

	public PQNode(E e, P p) {
		this.e = e;
		this.p = p;
	}

	public E getVertex() {
		return e;
	}

	public int index() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public P getPriority() {
		return p;
	}

	public void setPriority(P p) {
		this.p = p;
	}

	public VertexTraversalCode getCode() {
		return code;
	}

	public void setCode(VertexTraversalCode code) {
		this.code = code;
	}

	@Override
	public String toString() {

		return e.toString();
	}
}
