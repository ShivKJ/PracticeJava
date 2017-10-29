package algo.graphs.traversal.mst;

class IndexedPriorityQueueNode<E, P extends Comparable<P>> {
	private int		index;
	private final E	e;
	private P		p;

	public IndexedPriorityQueueNode(E e) {
		this.e = e;
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

	public P getPriority() {
		return p;
	}

	public void setPriority(P p) {
		this.p = p;
	}

}
