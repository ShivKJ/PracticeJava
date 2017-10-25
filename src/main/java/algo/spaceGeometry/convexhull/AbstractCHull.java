package algo.spaceGeometry.convexhull;

import java.util.Collection;

import algo.spaceGeometry.Point;

abstract class AbstractCHull<E extends Point> {
	protected final Collection<Point> input;

	@SuppressWarnings("unchecked")
	public AbstractCHull(Collection<? extends Point> input) {
		this.input = (Collection<Point>) input;
	}

	public abstract ConvexHull<E> getConvexHull();

}
