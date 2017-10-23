package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Utils.area;
import static algo.spaceGeometry.XY.E2;
import static algo.spaceGeometry.pointLocation.Locations.pointLocWrtToTriangle;
import static algo.spaceGeometry.pointLocation.PointLocation.OUTSIDE;
import static java.util.Collections.unmodifiableCollection;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import algo.spaceGeometry.XY;

public class ConvexHullJarvisOptimised extends ConvexHullJarvis {
	private XY a , b;

	public ConvexHullJarvisOptimised(Collection<? extends XY> input) {
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

	private final static class LabeledList extends ArrayList<XY> {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean add(XY e) {
			label(e);
			return super.add(e);
		}

		private void label(XY e) {
			if (e instanceof LabeledXY)
				((LabeledXY) e).inSystem = false;

		}

		@Override
		public boolean addAll(Collection<? extends XY> c) {
			forEach(this::label);
			return super.addAll(c);
		}

		@Override
		public boolean addAll(int index, Collection<? extends XY> c) {
			forEach(this::label);
			return super.addAll(index, c);
		}
	}

	@Override
	public List<XY> getConvexHull() {
		List<XY> convexHull = new LabeledList();

		if (!input.isEmpty()) {
			convexHull.add(origin);

			XY baseLine = E2;

			Optional<XY> nextB = null;
			Consumer<? super XY> labelPointsIfNotOutsideTriangle = new ElementLabeler()::label;

			while ((nextB = nextHullPoint(a, baseLine)).isPresent()) {
				b = nextB.get();
				convexHull.add(b);

				input.forEach(labelPointsIfNotOutsideTriangle);
				baseLine = a.to(b);
				a = b;
			}
			
			if (convexHull.size() > 1)
				convexHull.add(origin);
		}

		return convexHull;
	}

	@Override
	protected Optional<XY> nextHullPoint(XY src, XY baseLine) {
		return nextHullPoint(src, baseLine, x -> ((LabeledXY) x).inSystem);
	}

	private final class ElementLabeler {
		XY		b;
		double	area;

		ElementLabeler() {
			this.area = 0;
		}

		void recalculateArea() {
			this.area = area(origin, a, b);
		}

		boolean pointNotOutside(XY p) {

			XY outerClassB = ConvexHullJarvisOptimised.this.b;

			if (this.b != outerClassB) {
				this.b = outerClassB;
				recalculateArea();
			}
			return pointLocWrtToTriangle(origin, a, b, area, p) != OUTSIDE;
		}

		void label(XY x) {
			LabeledXY point = (LabeledXY) x;
			if (point.inSystem && pointNotOutside(x))
				point.inSystem = false;
		}
	}
}
