package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.PointUtils.line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import algo.spaceGeometry.Point;
import algo.spaceGeometry.pointLocation.Direction;
import algo.spaceGeometry.pointLocation.Locations;

public abstract class ConvexHull<E extends Point> extends ArrayList<E> implements Set<E> {

	private static final long	serialVersionUID	= 1L;
	protected boolean			closed;

	public ConvexHull() {}

	public ConvexHull(int size) {
		super(size);
	}

	public ConvexHull(List<? extends E> convexHull) throws NotConvexHullException, PolygonNotClosed {
		if (!isConvex(convexHull))
			throw new NotConvexHullException();

		super.addAll(convexHull);
		closed = true;
	}

	public final boolean isClosed() {
		return closed;
	}

	private static boolean isConvex(List<? extends Point> convexHull) throws PolygonNotClosed {
		Set<Point> linkedSet = new LinkedHashSet<>(convexHull);
		int size = linkedSet.size();

		if (size <= 1)
			return true;

		if (size == 2 && !convexHull.get(0).equals(convexHull.get(convexHull.size() - 1)))
			return false;// convex hull is not closed.
		else if (size > 2) {
			Iterator<? extends Point> iter = linkedSet.iterator();
			Point o = iter.next() , last = convexHull.get(convexHull.size() - 1) , a = iter.next();

			if (!last.equals(o))
				throw new PolygonNotClosed();

			Direction zDir = null;

			while (iter.hasNext()) {
				Direction tmp = Locations.crossProductZDirection(line(o, a), line(a, a = iter.next()));

				if (tmp != Direction.ZERO)
					if (zDir != null && zDir != tmp)
						return false;
					else
						zDir = tmp;
			}
		}

		return true;
	}

	@Override
	public Spliterator<E> spliterator() {
		return super.spliterator();
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
	public void replaceAll(UnaryOperator<E> operator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sort(Comparator<? super E> c) {
		throw new UnsupportedOperationException();
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

}
