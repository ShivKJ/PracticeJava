package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Point.E2;
import static algo.spaceGeometry.PointUtils.line;
import static algo.spaceGeometry.Utils.area;
import static algo.spaceGeometry.pointLocation.Locations.pointLocWrtToTriangle;
import static algo.spaceGeometry.pointLocation.PointLocation.OUTSIDE;
import static java.util.Collections.unmodifiableCollection;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

import algo.spaceGeometry.Point;
import algo.spaceGeometry.XY;

class CHullJarvisOptimized<E extends Point> extends CHullJarvis<E> {
	LabeledXY<E> a , b;

	@SuppressWarnings("unchecked")
	public CHullJarvisOptimized(Collection<? extends Point> input) {
		super(unmodifiableCollection(input.stream().map(LabeledXY::new).distinct().collect(toList())));
		this.a = (LabeledXY<E>) this.origin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConvexHull<E> getConvexHull() {
		ConvexHull<LabeledXY<E>> convexHull = new LabeledList<>();

		if (!input.isEmpty()) {
			convexHull.add((LabeledXY<E>) origin);

			Point baseLine = E2;

			Optional<Point> nextB = null;
			Consumer<? super Point> labelPointsIfNotOutsideTriangle = new ElementLabeler()::label;

			while ((nextB = nextHullPoint(a, baseLine)).isPresent()) {
				b = (LabeledXY<E>) nextB.get();
				convexHull.add(b);

				input.forEach(labelPointsIfNotOutsideTriangle);
				baseLine = line(a, b);
				a = b;
			}

			convexHull.add((LabeledXY<E>) origin);// closing convex hull
		}
		try {
			return new ConvexHullImpl<>(convexHull.stream().map(LabeledXY::getWrappedPoint).collect(toList()));
		} catch (NotConvexHullException | PolygonNotClosed e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Optional<Point> nextHullPoint(Point src, Point baseLine) {
		return nextHullPoint(src, baseLine, x -> ((LabeledXY<E>) x).inSystem);
	}

	private final static class LabeledXY<E extends Point> extends XY {
		final int	hashcode;
		boolean		inSystem;
		E			data;

		LabeledXY(E point) {
			super(point.X(), point.Y());
			this.data = point;
			this.hashcode = point.hashCode();
			this.inSystem = true;
		}

		@Override
		public int hashCode() {
			return hashcode;
		}

		public E getWrappedPoint() {
			return data;
		}

	}

	private final static class LabeledList<E extends Point> extends ConvexHullImpl<LabeledXY<E>> {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean add(LabeledXY<E> e) {
			boolean added = super.add(e);

			if (added)
				e.inSystem = false;

			return added;
		}
	}

	private final class ElementLabeler {
		LabeledXY<E>	b;
		double			area;

		ElementLabeler() {
			this.area = 0;
		}

		void recalculateArea() {
			this.area = area(origin, a, b);
		}

		boolean pointNotOutside(Point p) {

			LabeledXY<E> outerClassB = CHullJarvisOptimized.this.b;

			if (this.b != outerClassB) {
				this.b = outerClassB;
				recalculateArea();
			}
			return pointLocWrtToTriangle(origin, a, b, area, p) != OUTSIDE;
		}

		void label(Point x) {
			@SuppressWarnings("unchecked")
			LabeledXY<E> point = (LabeledXY<E>) x;
			if (point.inSystem && pointNotOutside(x))
				point.inSystem = false;
		}
	}
}
