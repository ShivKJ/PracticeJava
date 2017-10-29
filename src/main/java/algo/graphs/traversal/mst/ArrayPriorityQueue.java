package algo.graphs.traversal.mst;

import java.util.AbstractQueue;
import java.util.Iterator;

public class ArrayPriorityQueue<E extends IndexedPriorityQueueNode<?, P> & Comparable<E>, P extends Comparable<P>> extends AbstractQueue<E>
		implements AdaptablePriorityQueue<E, P> {
	private E[]	nodes;
	private int	effectiveSize;

	public ArrayPriorityQueue(E[] nodes) {
		this.nodes = nodes;
		this.effectiveSize = nodes.length;

		for (int i = 0; i < this.effectiveSize; i++)
			nodes[i].setIndex(i);
	}

	@Override
	public E poll() {
		E currNode = nodes[effectiveSize-- - 1];

		if (currNode.index() != 0)
			heapify(currNode);

		return currNode;
	}

	private void heapify(E node) {
		int index = node.index();
		E parent = nodes[(index + 1) / 2 - 1];

		if (parent != nodes[0])
			if (parent.compareTo(node) > 0) {
				swap(parent, node);
				heapify(parent);
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
	public void updatePriority(E e, P p) {}

	@Override
	public int size() {

		return effectiveSize;
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
	public Iterator<E> iterator() {
		throw new UnsupportedOperationException();
	}

}
