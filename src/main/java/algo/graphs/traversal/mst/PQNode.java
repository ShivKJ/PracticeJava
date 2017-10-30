package algo.graphs.traversal.mst;

import algo.graphs.traversal.VertexTraversalCode;

class PQNode<E> {
	private int					index;
	private final E				e;
	private Object				priority;
	private VertexTraversalCode	code;

	public <P extends Comparable<P>> PQNode(E e, P p) {
		this.e = e;
		this.priority = p;
	}

	public E getData() {
		return e;
	}

	public int index() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@SuppressWarnings("unchecked")
	public <P extends Comparable<P>> P getPriority() {
		return (P) priority;
	}

	public <P extends Comparable<P>> void setPriority(P p) {
		this.priority = p;
	}

	public VertexTraversalCode code() {
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
