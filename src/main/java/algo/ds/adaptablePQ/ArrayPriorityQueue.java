package algo.ds.adaptablePQ;

import static algo.graphs.traversal.VertexTraversalCode.NOT_IN_PRIM_PRIORITY_QUEUE;
import static java.util.Collections.emptyList;

import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class ArrayPriorityQueue<E extends IndexedPNode> extends AbstractQueue<E> implements AdaptablePriorityQueue<E> {

	private final ArrayList<E>	nodes;
	private int					effectiveSize;
	private final Comparator<E>	compNodes;

	@SuppressWarnings("unchecked")
	public ArrayPriorityQueue(Collection<? extends E> nodes, Comparator<? super E> comparator) {
		this.compNodes = (Comparator<E>) comparator;

		this.nodes = new ArrayList<>(nodes.size());
		this.effectiveSize = 0;

		addAll(nodes);
	}

	public ArrayPriorityQueue(Collection<? extends E> nodes) {
		this(nodes, E::compareTo);
	}

	public ArrayPriorityQueue() {
		this(emptyList());
	}

	@Override
	public <P extends Comparable<P>> void updatePriority(E e, P p) {

		P newPriority = p;
		P oldPriority = e.getPriority();

		e.setPriority(newPriority);
		compNodes.compare(e, e);
		int comp = newPriority.compareTo(oldPriority);

		if (comp < 0)
			bubbleUp(e);
		else if (comp > 0)
			bubbleDown(e);
	};

	@Override
	public E poll() {
		E currNode = nodes.get(0);

		int lastIndex = effectiveSize - 1;

		swap(currNode, nodes.get(lastIndex));

		nodes.set(--effectiveSize, null);

		if (!isEmpty())
			bubbleDown(nodes.get(0));

		currNode.setCode(NOT_IN_PRIM_PRIORITY_QUEUE);

		return currNode;
	}

	private void bubbleDown(E node) {
		int index = node.index() , leftChildIndex = 2 * index + 1 , rightChildIndex = leftChildIndex + 1;
		E min = node;

		if (leftChildIndex < effectiveSize && compNodes.compare(min, nodes.get(leftChildIndex)) > 0)
			min = nodes.get(leftChildIndex);

		if (rightChildIndex < effectiveSize && compNodes.compare(min, nodes.get(rightChildIndex)) > 0)
			min = nodes.get(rightChildIndex);

		if (min != node) {
			swap(node, min);
			bubbleDown(node);
		}

	}

	private void bubbleUp(E node) {
		int index = node.index();

		while (index != 0) {
			E parent = nodes.get((index + 1) / 2 - 1);

			if (compNodes.compare(parent, node) > 0) {
				swap(parent, node);
				index = node.index();
			} else
				break;
		}
	}

	private void swap(E a, E b) {
		int aIndex = a.index() , bIndex = b.index();

		a.setIndex(bIndex);
		b.setIndex(aIndex);

		nodes.set(aIndex, b);
		nodes.set(bIndex, a);

	}

	@Override
	public boolean add(E e) {
		e.setIndex(effectiveSize);

		if (effectiveSize < nodes.size())
			nodes.set(effectiveSize, e);
		else
			nodes.add(e);

		effectiveSize++;

		bubbleUp(e);

		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {

		int oldSize = effectiveSize;

		nodes.ensureCapacity(effectiveSize + c.size());

		c.forEach(this::add);

		return oldSize != effectiveSize;
	}

	@Override
	public String toString() {

		return nodes.toString();
	}

	@Override
	public int size() {
		return effectiveSize;
	}

	@Override
	public boolean contains(Object o) {
		return o instanceof IndexedPNode && ((IndexedPNode) o).code() != NOT_IN_PRIM_PRIORITY_QUEUE;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			Iterator<E>	iterator	= nodes.iterator();
			E			next		= iterator.hasNext() ? iterator.next() : null;

			@Override
			public boolean hasNext() {

				return next != null;
			}

			@Override
			public E next() {
				E tmp = next;
				next = iterator.hasNext() ? iterator.next() : null;
				return tmp;
			}

		};
	}

	@Override
	public boolean offer(E e) {
		return add(e);
	}

	@Override
	public E peek() {
		return nodes.get(0);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Object[] toArray() {
		Object[] output = new Object[effectiveSize];

		for (int i = 0; i < effectiveSize; i++)
			output[i] = nodes.get(i);

		return output;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size())
			a = (T[]) Array.newInstance(a.getClass().getComponentType(), effectiveSize);

		for (int i = 0; i < effectiveSize; i++)
			a[i] = (T) nodes.get(i);

		return a;

	}

	@Override
	public void clear() {
		nodes.clear();
		effectiveSize = 0;
	}

}
