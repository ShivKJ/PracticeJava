package algo.graphs.traversal.mst;

import algo.graphs.traversal.TraversalVertex;

class PQNode<P extends Comparable<P>> {
	private int							index;
	private final TraversalVertex<?>	e;
	private P							p;

	public PQNode(TraversalVertex<?> e, P p) {
		this.e = e;
		this.p = p;
	}

	public TraversalVertex<?> getVertex() {
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
}
