package algo.ds.adaptablePQ;

public class PQNode<E, K extends Comparable<K>> extends IndexedPNode implements PNode {
	private final E	e;
	private K		priority;

	public PQNode(E e, K p) {
		this.e = e;
		this.priority = p;
	}

	public E getData() {

		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Q extends Comparable<Q>> void setPriority(Q p) {
		this.priority = (K) p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Q extends Comparable<Q>> Q getPriority() {

		return (Q) priority;
	}

}
