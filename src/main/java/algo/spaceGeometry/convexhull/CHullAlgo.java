package algo.spaceGeometry.convexhull;

import java.util.Collection;

import algo.spaceGeometry.Boundary;
import algo.spaceGeometry.XY;

public abstract class CHullAlgo<E extends XY> {
	protected final Collection<E> input;

	@SuppressWarnings("unchecked")
	public CHullAlgo(Collection<? extends XY> input) {
		this.input = (Collection<E>) input;
	}

	static class A extends XY {

		public A(double x, double y) {

			super(x, y);
		}
	}

	public abstract Boundary<E> getConvexHull();

}
