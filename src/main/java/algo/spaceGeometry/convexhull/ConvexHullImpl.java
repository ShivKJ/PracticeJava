package algo.spaceGeometry.convexhull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import algo.spaceGeometry.Point;

class ConvexHullImpl<E extends Point> extends ConvexHull<E> {
	private static final long	serialVersionUID	= 1L;
	private final Set<E>		wrapperSet;

	public ConvexHullImpl(int size) {
		super(size);
		this.wrapperSet = new HashSet<>(size);
		this.closed = false;
	}

	public ConvexHullImpl() {
		this.wrapperSet = new HashSet<>();
		this.closed = false;
	}

	public ConvexHullImpl(List<? extends E> convexHull) throws NotConvexHullException, PolygonNotClosed {
		super(convexHull);
		this.wrapperSet = new HashSet<>(convexHull);
	}

	@Override
	public boolean add(E e) {
		if (closed)
			try {
				throw new ConvexHullClosed();
			} catch (ConvexHullClosed e2) {
				e2.printStackTrace();
			}

		if (isEmpty())
			return super.add(e) && wrapperSet.add(e);

		E first = get(0);

		if (wrapperSet.contains(e))
			if (!e.equals(first))
				try {
					throw new ConvexHullAlreadyContainsPoint();
				} catch (ConvexHullAlreadyContainsPoint e2) {
					e2.printStackTrace();
				}
			else
				closed = true;

		wrapperSet.add(e);
		super.add(e);
		return true;
	}

	@Override
	public boolean contains(Object o) {

		return wrapperSet.contains(o);
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof ConvexHull && ((ConvexHull<?>) o).size() == size() && super.equals(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {

		return wrapperSet.containsAll(c);
	}

}
