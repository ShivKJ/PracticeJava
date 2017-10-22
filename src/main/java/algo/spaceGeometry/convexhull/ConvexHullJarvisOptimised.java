package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.PointLocation.INSIDE;
import static algo.spaceGeometry.PointLocation.ON;
import static algo.spaceGeometry.PointLocation.OUTSIDE;
import static algo.spaceGeometry.Utils.area;
import static algo.spaceGeometry.Utils.crossProductZDirection;
import static algo.spaceGeometry.Utils.isZero;
import static algo.spaceGeometry.Utils.pointLocWRTLineSegment;
import static algo.spaceGeometry.XY.E2;
import static algo.spaceGeometry.ZDirection.UNDEFINED;
import static java.util.Collections.unmodifiableCollection;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import algo.spaceGeometry.PointLocation;
import algo.spaceGeometry.XY;
import algo.spaceGeometry.ZDirection;

public class ConvexHullJarvisOptimised extends ConvexHullJarvis {
	private XY a , b;

	public ConvexHullJarvisOptimised(Collection<? extends XY> input) throws EmptyCollectionException {
		super(unmodifiableCollection(input.stream().map(XYHashed::new).distinct().collect(toList())));
		this.a = this.origin;
	}

	private final static class XYHashed extends XY {
		transient final int	hashcode;
		transient boolean	inSystem;

		XYHashed(double x, double y) {
			super(x, y);
			this.hashcode = super.hashCode();
			this.inSystem = true;
		}

		XYHashed(XY point) {
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
			if (e instanceof XYHashed)
				((XYHashed) e).inSystem = false;

			return super.add(e);
		}
	}

	@Override
	public List<XY> getConvexHull() {
		List<XY> convexHull = new LabeledList();
		convexHull.add(origin);

		XY baseLine = new XYHashed(E2);

		Optional<? extends XY> nextB = null;
		Consumer<? super XY> labelPointsIfNotOutsideTriangle = labelPointsNotOusideTriangle(new LocationFindingTriangle());

		while ((nextB = nextHullPoint(a, baseLine)).isPresent()) {
			b = nextB.get();
			convexHull.add(b);

			input.forEach(labelPointsIfNotOutsideTriangle);
			baseLine = a.to(b);
			a = b;
		}

		if (convexHull.size() > 1)
			convexHull.add(origin);

		return convexHull;
	}

	private Consumer<? super XY> labelPointsNotOusideTriangle(LocationFindingTriangle triangle) {
		return x -> {
			XYHashed point = (XYHashed) x;
			if (point.inSystem && triangle.pointNotOutside(x))
				point.inSystem = false;
		};
	}

	@Override
	protected Optional<? extends XY> nextHullPoint(XY src, XY baseLine) {
		return nextHullPoint(src, baseLine, x -> ((XYHashed) x).inSystem);
	}

	private final class LocationFindingTriangle {
		XY		b;
		double	area;

		LocationFindingTriangle() {
			this.area = 0;
		}

		void recalculateArea() {
			this.area = area(origin, a, b);
		}

		PointLocation getPointLocation(XY p) {
			XY outerClassB = ConvexHullJarvisOptimised.this.b;

			if (this.b != outerClassB) {
				this.b = outerClassB;
				recalculateArea();
			}

			XY pa = p.to(a) , pb = p.to(b) , pc = p.to(origin);
			if (isZero(area))
				return pointLocWRTLineSegment(a.to(b), pa, pb) == ON || pointLocWRTLineSegment(b.to(origin), pb, pc) == ON
						? ON
						: OUTSIDE;

			ZDirection ab = crossProductZDirection(pa, pb);
			if (ab == UNDEFINED)
				return pointLocWRTLineSegment(a.to(b), pa, pb);

			ZDirection bc = crossProductZDirection(pb, pc);
			if (bc == UNDEFINED)
				return pointLocWRTLineSegment(b.to(origin), pb, pc);

			ZDirection ca = crossProductZDirection(pc, pa);
			if (ca == UNDEFINED)
				return pointLocWRTLineSegment(origin.to(a), pc, pa);

			return ab == bc && bc == ca ? INSIDE : OUTSIDE;
		}

		boolean pointNotOutside(XY p) {
			return getPointLocation(p) != OUTSIDE;
		}

	}
}
