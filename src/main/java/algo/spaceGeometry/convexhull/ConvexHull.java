package algo.spaceGeometry.convexhull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import algo.spaceGeometry.Boundary;
import algo.spaceGeometry.XY;

public class ConvexHull<E extends XY> extends ArrayList<E> implements Boundary<E> {
	private static final long	serialVersionUID	= 1L;
	private final Set<E>		wrapperSet;

	public ConvexHull(int size) {
		super(size);
		this.wrapperSet = new HashSet<>(size);
	}

	public ConvexHull() {
		this.wrapperSet = new HashSet<>();
	}

	public ConvexHull(ConvexHull<? extends E> convexHull) {
		super(convexHull);
		this.wrapperSet = new HashSet<>(convexHull.wrapperSet);
	}

//	public ConvexHull(List<? extends E> convexHull) {
//		super(convexHull);
//		this.wrapperSet = new HashSet<>(convexHull);
//	}

	@Override
	public boolean add(E e) {
		if (!wrapperSet.contains(e) || get(0).equals(e) && size() > 1 && !get(size() - 1).equals(e)) {
			wrapperSet.add(e);
			return super.add(e);
		}
		return false;

	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {

		return wrapperSet.containsAll(c);
	}

	@Override
	public boolean contains(Object o) {

		return wrapperSet.contains(o);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Boundary && ((Boundary<?>) o).size() == size() && super.equals(o);
	}

	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {

		throw new UnsupportedOperationException();
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E set(int index, E element) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ConvexHull<E> clone() {
		return new ConvexHull<>(this);
	}

	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sort(Comparator<? super E> c) {
		throw new UnsupportedOperationException();
	}

}
