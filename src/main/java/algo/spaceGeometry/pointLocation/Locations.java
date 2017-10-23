package algo.spaceGeometry.pointLocation;

import static algo.spaceGeometry.Utils.area;
import static algo.spaceGeometry.Utils.crossProduct;
import static algo.spaceGeometry.Utils.isEqual;
import static algo.spaceGeometry.Utils.isZero;
import static algo.spaceGeometry.pointLocation.Direction.MINUS;
import static algo.spaceGeometry.pointLocation.Direction.PLUS;
import static algo.spaceGeometry.pointLocation.Direction.ZERO;
import static algo.spaceGeometry.pointLocation.PointLocation.INSIDE;
import static algo.spaceGeometry.pointLocation.PointLocation.ON;
import static algo.spaceGeometry.pointLocation.PointLocation.OUTSIDE;
import static java.lang.Math.abs;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.groupingBy;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import algo.spaceGeometry.XY;

public final class Locations {

	public static PointLocation pointWrtCHull(List<? extends XY> convexHull, XY p) {
		/*
		 * Convex hull should be non empty and each point in convex hull should be distinct
		 * and area of it should be defined and non zero if hull size > 2.
		 */

		int size = convexHull.size();

		Iterator<? extends XY> iter = convexHull.iterator();

		XY a = iter.next();

		if (size == 1)
			return a.equals(p) ? ON : OUTSIDE;

		XY b = iter.next() , pa = p.to(a) , pb = p.to(b);

		Direction direction = crossProductZDirection(pa, pb);

		if (direction == ZERO)
			return pointLocWrtLineSegment(a.to(b), pa, pb);

		if (size == 3)// size 2 will not be possible.
			return OUTSIDE;

		while (iter.hasNext()) {
			XY x = iter.next() , px = p.to(x);

			Direction bCrossX = crossProductZDirection(pb, px);

			if (bCrossX == ZERO)
				return pointLocWrtLineSegment(b.to(x), pb, px);
			else if (direction != bCrossX)
				return OUTSIDE;

			b = x;
			pb = px;
		}

		return INSIDE;
	}

	public static PointLocation pointLocWrtLineSegment(XY ab, XY pa, XY pb) {
		return isEqual(abs(ab.X()), abs(pa.X()) + abs(pb.X())) && isEqual(abs(ab.Y()), abs(pa.Y()) + abs(pb.Y())) ? ON : OUTSIDE;
	}

	public static PointLocation pointLocWrtToTriangle(XY a, XY b, XY c, double area, XY p) {
		if (isZero(area)) {
			XY pb = p.to(b);
			return pointLocWrtLineSegment(a.to(b), p.to(a), pb) == ON || pointLocWrtLineSegment(b.to(c), pb, p.to(c)) == ON
					? ON
					: OUTSIDE;
		}

		return pointWrtCHull(asList(a, b, c, a), p);
	}

	private static Direction crossProductZDirection(XY a, XY b) {
		double crossProduct = crossProduct(a, b);

		if (isZero(crossProduct))
			return ZERO;

		return crossProduct > 0 ? PLUS : MINUS;
	}

	public static PointLocation pointLocWrtTriangle(XY a, XY b, XY c, XY p) {
		return pointLocWrtToTriangle(a, b, c, area(a, b, c), p);
	}

	public static Direction pointLocWrtLine(XY a, XY b, XY p) {
		return crossProductZDirection(p.to(a), p.to(b));
	}

	public static Map<Direction, List<XY>> partitionPointsWrtLine(XY a, XY b, Collection<? extends XY> points) {
		return points.stream().collect(groupingBy(p -> pointLocWrtLine(a, b, p)));
	}

	public static Map<PointLocation, List<XY>> partitionPointWrtCHull(List<? extends XY> cHull, Collection<? extends XY> points) {
		return points.stream().collect(groupingBy(p -> pointWrtCHull(cHull, p)));
	}
}
