package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Utils.area;
import static algo.spaceGeometry.XY.E2;
import static algo.spaceGeometry.pointLocation.Locations.pointLocWrtToTriangle;
import static algo.spaceGeometry.pointLocation.PointLocation.OUTSIDE;
import static java.util.Collections.unmodifiableCollection;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import algo.spaceGeometry.Boundary;
import algo.spaceGeometry.XY;

class CHullOptimized<E extends XY> extends CHullJarvis<E> {
	private E a , b;

	public CHullOptimized(Collection<? extends XY> input) {
		super(unmodifiableCollection(input.stream().map(LabeledXY::new).distinct().collect(toList())));
		this.a = this.origin;
	}

	private final static class LabeledXY extends XY {
		transient final int	hashcode;
		transient boolean	inSystem;

		LabeledXY(double x, double y) {
			super(x, y);
			this.hashcode = super.hashCode();
			this.inSystem = true;
		}

		LabeledXY(XY point) {
			this(point.X(), point.Y());
		}

		@Override
		public int hashCode() {
			return hashcode;
		}

	}

	private final static class LabeledList<E extends XY> extends ConvexHull<E> {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean add(E e) {
			boolean added = super.add(e);

			if (added)
				label(e);

			return added;
		}

		void label(E e) {
			if (e instanceof LabeledXY)
				((LabeledXY) e).inSystem = false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Boundary<E> getConvexHull() {
		Boundary<E> convexHull = new LabeledList<>();

		if (!input.isEmpty()) {
			convexHull.add(origin);

			E baseLine = (E) E2;

			Optional<E> nextB = null;
			Consumer<? super E> labelPointsIfNotOutsideTriangle = new ElementLabeler()::label;

			while ((nextB = nextHullPoint(a, baseLine)).isPresent()) {
				b = nextB.get();
				convexHull.add(b);

				input.forEach(labelPointsIfNotOutsideTriangle);
				baseLine = (E) a.to(b);
				a = b;
			}

			convexHull.add(origin);// closing convex hull
		}

		return convexHull;
	}

	@Override
	protected Optional<E> nextHullPoint(E src, E baseLine) {
		return nextHullPoint(src, baseLine, x -> ((LabeledXY) x).inSystem);
	}

	private final class ElementLabeler {
		E		b;
		double	area;

		ElementLabeler() {
			this.area = 0;
		}

		void recalculateArea() {
			this.area = area(origin, a, b);
		}

		boolean pointNotOutside(XY p) {

			E outerClassB = CHullOptimized.this.b;

			if (this.b != outerClassB) {
				this.b = outerClassB;
				recalculateArea();
			}
			return pointLocWrtToTriangle(origin, a, b, area, p) != OUTSIDE;
		}

		void label(E x) {
			LabeledXY point = (LabeledXY) x;
			if (point.inSystem && pointNotOutside(x))
				point.inSystem = false;
		}
	}
}
