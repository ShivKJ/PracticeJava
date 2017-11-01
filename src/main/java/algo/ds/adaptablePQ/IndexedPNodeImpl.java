package algo.ds.adaptablePQ;

public class IndexedPNodeImpl<E, K extends Comparable<K>> extends IndexedPNode<E, K> {
	private final E	e;
	private K		priority;

	public IndexedPNodeImpl(E e, K p) {
		this.e = e;
		this.priority = p;
	}

	@Override
	public E getData() {

		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setPriority(Object p) {
		this.priority = (K) p;
	}

	@Override
	public K getPriority() {

		return priority;
	}

}
