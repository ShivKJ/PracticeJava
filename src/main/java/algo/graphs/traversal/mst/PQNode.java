package algo.graphs.traversal.mst;

class PQNode<E> extends IndexedNode implements PNode {
	private final E	e;
	private Object	priority;

	public <P extends Comparable<P>> PQNode(E e, P p) {
		this.e = e;
		this.priority = p;
	}

	public E getData() {
		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P extends Comparable<P>> P getPriority() {
		return (P) priority;
	}

	@Override
	public <P extends Comparable<P>> void setPriority(P p) {
		this.priority = p;
	}

	@Override
	public String toString() {
		return e.toString();
	}

}
