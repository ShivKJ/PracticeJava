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
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import algo.spaceGeometry.PointLocation;
import algo.spaceGeometry.XY;
import algo.spaceGeometry.ZDirection;

public class ConvexHullJarvisOptimised extends ConvexHullJarvis {
	private final Set<XY>	convexHull;
	private XY				a , b;

	public ConvexHullJarvisOptimised(Collection<? extends XY> input) throws EmptyCollectionException {
		super(input.stream().map(XYHashed::new).collect(toSet()));
		this.convexHull = new LinkedHashSet<>();
		this.a = this.origin;
	}

	private final static class XYHashed extends XY {
		private transient final int hashcode;

		XYHashed(double x, double y) {
			super(x, y);
			this.hashcode = super.hashCode();
		}

		XYHashed(XY point) {
			this(point.X(), point.Y());
		}

		@Override
		public int hashCode() {
			return hashcode;
		}

	}

	@Override
	public List<XY> getConvexHull() {
		convexHull.add(origin);
		XY baseLine = new XYHashed(E2);

		Optional<? extends XY> nextB = null;
		LocationFindingTriangle triangle = new LocationFindingTriangle();
		Predicate<? super XY> pointNotOutside = pointNotOutside(triangle);

		while ((nextB = nextHullPoint(a, baseLine)).isPresent() && (b = nextB.get()) != origin) {
			triangle.recalculateArea();

			convexHull.add(b);

			input.removeIf(pointNotOutside);

			baseLine = a.to(b);
			a = b;
		}

		return output();
	}

	@Override
	protected Optional<? extends XY> nextHullPoint(XY src, XY baseLine) {
		return nextHullPoint(src, baseLine, x -> x != src);
	}

	@Override
	protected Optional<? extends XY> bestPoint(Predicate<? super XY> filter, Comparator<? super XY> comp) {
		XY bestPoint = null;

		for (XY currentPoint : input)
			if (filter.test(currentPoint))
				if (bestPoint == null) {
					bestPoint = currentPoint;
					continue;
				} else if (comp.compare(bestPoint, currentPoint) < 0)
					bestPoint = currentPoint;

		return ofNullable(bestPoint);
	}

	private List<XY> output() {
		List<XY> output = new ArrayList<>(convexHull);

		if (output.size() > 1)
			output.add(origin);// closing convex hull

		return output;
	}

	private Predicate<? super XY> pointNotOutside(LocationFindingTriangle triangle) {
		return x -> !convexHull.contains(x) && triangle.pointNotOutside(x);
	}

	private final class LocationFindingTriangle {

		double area;

		LocationFindingTriangle() {
			this.area = 0;
		}

		void recalculateArea() {
			this.area = area(origin, a, b);
		}

		PointLocation getPointLocation(XY p) {
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
