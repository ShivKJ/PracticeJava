package algo.spaceGeometry.convexhull;

import java.util.Collection;

import algo.spaceGeometry.Boundary;
import algo.spaceGeometry.Point;

abstract class CHull<E extends Point> {
	protected final Collection<Point> input;

	@SuppressWarnings("unchecked")
	public CHull(Collection<? extends Point> input) {
		this.input = (Collection<Point>) input;
	}

	public abstract Boundary<E> getConvexHull();

}
