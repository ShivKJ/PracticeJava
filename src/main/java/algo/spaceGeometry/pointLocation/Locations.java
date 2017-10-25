package algo.spaceGeometry.pointLocation;

import static algo.spaceGeometry.PointUtils.crossProduct;
import static algo.spaceGeometry.PointUtils.line;
import static algo.spaceGeometry.Utils.area;
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

import algo.spaceGeometry.Point;

public final class Locations {

	public static PointLocation pointWrtCHull(List<? extends Point> convexHull, Point p) {
		/*
		 * Convex hull should be non empty and each point in convex hull should be distinct
		 * and area of it should be defined and non zero if hull size > 2.
		 */

		int size = convexHull.size();

		Iterator<? extends Point> iter = convexHull.iterator();

		Point a = iter.next();

		if (size == 1)
			return a.equals(p) ? ON : OUTSIDE;

		Point b = iter.next() , pa = line(p, a) , pb = line(p, b);

		Direction direction = crossProductZDirection(pa, pb);

		if (direction == ZERO)
			return pointLocWrtLineSegment(line(a, b), pa, pb);

		if (size == 3)// size 2 will not be possible.
			return OUTSIDE;

		while (iter.hasNext()) {
			Point x = iter.next() , px = line(p, x);

			Direction bCrossX = crossProductZDirection(pb, px);

			if (bCrossX == ZERO)
				return pointLocWrtLineSegment(line(b, x), pb, px);
			else if (direction != bCrossX)
				return OUTSIDE;

			b = x;
			pb = px;
		}

		return INSIDE;
	}

	public static PointLocation pointLocWrtLineSegment(Point ab, Point pa, Point pb) {
		return isEqual(abs(ab.X()), abs(pa.X()) + abs(pb.X())) && isEqual(abs(ab.Y()), abs(pa.Y()) + abs(pb.Y())) ? ON : OUTSIDE;
	}

	public static PointLocation pointLocWrtToTriangle(Point a, Point b, Point c, double area, Point p) {
		if (isZero(area)) {
			Point pb = line(p, b);
			return pointLocWrtLineSegment(line(a, b), line(p, a), pb) == ON || pointLocWrtLineSegment(line(b, c), pb, line(p, c)) == ON
					? ON
					: OUTSIDE;
		}
		return pointWrtCHull(asList(a, b, c, a), p);
	}

	private static Direction crossProductZDirection(Point a, Point b) {
		double crossProduct = crossProduct(a, b);

		if (isZero(crossProduct))
			return ZERO;

		return crossProduct > 0 ? PLUS : MINUS;
	}

	public static PointLocation pointLocWrtTriangle(Point a, Point b, Point c, Point p) {
		return pointLocWrtToTriangle(a, b, c, area(a, b, c), p);
	}

	public static Direction pointLocWrtLine(Point a, Point b, Point p) {
		return crossProductZDirection(line(p, a), line(p, b));
	}

	public static Map<Direction, List<Point>> partitionPointsWrtLine(Point a, Point b, Collection<? extends Point> points) {
		return points.stream().collect(groupingBy(p -> pointLocWrtLine(a, b, p)));
	}

	public static Map<PointLocation, List<Point>> partitionPointWrtCHull(List<? extends Point> cHull, Collection<? extends Point> points) {
		return points.stream().collect(groupingBy(p -> pointWrtCHull(cHull, p)));
	}
}
