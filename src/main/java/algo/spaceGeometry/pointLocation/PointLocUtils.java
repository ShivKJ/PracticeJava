package algo.spaceGeometry.pointLocation;

import static algo.spaceGeometry.Utils.area;
import static algo.spaceGeometry.Utils.crossProduct;
import static algo.spaceGeometry.Utils.isEqual;
import static algo.spaceGeometry.Utils.isZero;
import static algo.spaceGeometry.pointLocation.PointLocation.INSIDE;
import static algo.spaceGeometry.pointLocation.PointLocation.ON;
import static algo.spaceGeometry.pointLocation.PointLocation.OUTSIDE;
import static algo.spaceGeometry.pointLocation.ZDirection.DOWN;
import static algo.spaceGeometry.pointLocation.ZDirection.UNDEFINED;
import static algo.spaceGeometry.pointLocation.ZDirection.UP;
import static java.lang.Math.abs;
import static java.util.Arrays.asList;

import java.util.Iterator;
import java.util.List;

import algo.spaceGeometry.XY;

public final class PointLocUtils {

	public static PointLocation pointWrtConvexHull(List<XY> convexHull, XY p) {
		/*
		 * Each point in convex hull should be distinct, except for start and end points in case size of list is greater
		 * than 2 and on line segment formed by any two point does not contain a
		 * point from convex hull, And hull should be non empty.
		 */

		int size = convexHull.size();

		Iterator<XY> iter = convexHull.iterator();

		XY a = iter.next();

		if (size == 1)
			return a.equals(p) ? ON : OUTSIDE;

		XY b = iter.next() , ab = a.to(b) , pa = p.to(a) , pb = p.to(b);

		ZDirection direction = crossProductZDirection(pa, pb);

		if (direction == UNDEFINED)
			return pointLocWRTLineSegment(ab, pa, pb);

		if (size == 3)// size 2 will not be possible.
			return OUTSIDE;

		while (iter.hasNext()) {
			XY x = iter.next() , px = p.to(x);

			ZDirection bCrossX = crossProductZDirection(pb, px);

			if (bCrossX == UNDEFINED)
				return pointLocWRTLineSegment(b.to(x), pb, px);
			else if (direction != bCrossX)
				return OUTSIDE;

			b = x;
			pb = px;
		}

		return INSIDE;
	}

	public static PointLocation pointLocWRTLineSegment(XY ab, XY pa, XY pb) {
		return isEqual(abs(ab.X()), abs(pa.X()) + abs(pb.X())) && isEqual(abs(ab.Y()), abs(pa.Y()) + abs(pb.Y())) ? ON : OUTSIDE;
	}

	public static PointLocation pointLocWrtToTriangle(XY a, XY b, XY c, double area, XY p) {
		if (isZero(area)) {
			XY pa = p.to(a) , pb = p.to(b) , pc = p.to(c);
			return pointLocWRTLineSegment(a.to(b), pa, pb) == ON || pointLocWRTLineSegment(b.to(c), pb, pc) == ON
					? ON
					: OUTSIDE;
		}
		
		return pointWrtConvexHull(asList(a, b, c, a), p);
	}

	public static ZDirection crossProductZDirection(XY a, XY b) {
		double crossProduct = crossProduct(a, b);

		if (isZero(crossProduct))
			return UNDEFINED;

		return crossProduct > 0 ? UP : DOWN;
	}

	public static PointLocation pointLocWrtToTriangle(XY a, XY b, XY c, XY p) {
		return pointLocWrtToTriangle(a, b, c, area(a, b, c), p);
	}
}
