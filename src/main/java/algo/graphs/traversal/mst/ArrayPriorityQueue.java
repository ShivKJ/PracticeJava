package algo.graphs.traversal.mst;

import static java.util.Arrays.sort;
import static java.util.Comparator.comparing;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

class ArrayPriorityQueue<E extends PQNode<P>, P extends Comparable<P>> implements AdaptablePriorityQueue<E> {

	private final E[]			nodes;
	private int					effectiveSize;
	private final Comparator<E>	compNodes;

	ArrayPriorityQueue(E[] nodes) {
		this.compNodes = comparing(E::getPriority);

		sort(nodes, this.compNodes);

		this.nodes = nodes;
		this.effectiveSize = nodes.length;

		for (int i = 0; i < this.effectiveSize; i++)
			nodes[i].setIndex(i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updatePriority(E e, Comparable<?> p) {
		P oldPriority = e.getPriority();
		P newPriority = (P) p;
		e.setPriority(newPriority);

		int comp = newPriority.compareTo(oldPriority);

		if (comp < 0)
			bubbleUp(e);
		else if (comp > 0)
			bubbleDown(e);
	}

	@Override
	public E poll() {
		E currNode = nodes[0];
		int lastIndex = --effectiveSize;

		nodes[0] = nodes[lastIndex];

		nodes[lastIndex] = null;
		bubbleDown(nodes[0]);

		return currNode;
	}

	private void bubbleDown(E node) {
		int index = node.index() , leftChildIndex = 2 * index + 1 , rightChildIndex = leftChildIndex + 1;

		E min = node;

		if (leftChildIndex < effectiveSize && compNodes.compare(min, nodes[leftChildIndex]) > 0)
			min = nodes[leftChildIndex];

		if (rightChildIndex < effectiveSize && compNodes.compare(min, nodes[rightChildIndex]) > 0)
			min = nodes[rightChildIndex];

		if (min != node) {
			swap(node, min);
			bubbleDown(node);
		}

	}

	private void bubbleUp(E node) {
		int index = node.index();

		while (index != 0) {
			E parent = nodes[(index + 1) / 2 - 1];

			if (compNodes.compare(parent, node) > 0) {
				swap(parent, node);
				bubbleUp(parent);
				index = node.index();
			} else
				break;
		}
	}

	private void swap(E a, E b) {
		int aIndex = a.index() , bIndex = b.index();

		a.setIndex(bIndex);
		b.setIndex(aIndex);

		nodes[aIndex] = b;
		nodes[bIndex] = a;

	}

	@Override
	public int size() {
		return effectiveSize;
	}

	@Override
	public boolean contains(Object o) {
		return o instanceof PQNode && ((PQNode<?>) o).index() < size();
	}

	@Override
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean offer(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E peek() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public E element() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}
}
