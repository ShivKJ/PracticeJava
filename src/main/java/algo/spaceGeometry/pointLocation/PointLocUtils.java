package algo.spaceGeometry.pointLocation;

import static algo.spaceGeometry.Utils.area;
import static algo.spaceGeometry.Utils.crossProductZDirection;
import static algo.spaceGeometry.Utils.isZero;
import static algo.spaceGeometry.ZDirection.UNDEFINED;
import static algo.spaceGeometry.pointLocation.PointLocation.INSIDE;
import static algo.spaceGeometry.pointLocation.PointLocation.ON;
import static algo.spaceGeometry.pointLocation.PointLocation.OUTSIDE;
import static java.lang.Math.abs;

import java.util.Iterator;
import java.util.List;

import algo.spaceGeometry.Utils;
import algo.spaceGeometry.XY;
import algo.spaceGeometry.ZDirection;

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

		ZDirection direction = Utils.crossProductZDirection(pa, pb);

		if (direction == UNDEFINED)
			return PointLocUtils.pointLocWRTLineSegment(ab, pa, pb);

		if (size == 3)// size 2 will not be possible.
			return OUTSIDE;

		while (iter.hasNext()) {
			XY x = iter.next() , px = p.to(x);

			ZDirection bCrossX = Utils.crossProductZDirection(pb, px);

			if (bCrossX == UNDEFINED)
				return PointLocUtils.pointLocWRTLineSegment(b.to(x), pb, px);
			else if (direction != bCrossX)
				return OUTSIDE;

			b = x;
			pb = px;
		}

		return INSIDE;
	}

	public static PointLocation pointLocWRTLineSegment(XY ab, XY pa, XY pb) {
		return Utils.isEqual(abs(ab.X()), abs(pa.X()) + abs(pb.X())) && Utils.isEqual(abs(ab.Y()), abs(pa.Y()) + abs(pb.Y())) ? ON : OUTSIDE;
	}

	public static PointLocation pointLocWrtToTriangle(XY a, XY b, XY c, double area, XY p) {
		XY pa = p.to(a) , pb = p.to(b) , pc = p.to(c);
		if (isZero(area))
			return PointLocUtils.pointLocWRTLineSegment(a.to(b), pa, pb) == ON || PointLocUtils.pointLocWRTLineSegment(b.to(c), pb, pc) == ON
					? ON
					: OUTSIDE;

		ZDirection ab = crossProductZDirection(pa, pb);
		if (ab == UNDEFINED)
			return PointLocUtils.pointLocWRTLineSegment(a.to(b), pa, pb);

		ZDirection bc = crossProductZDirection(pb, pc);
		if (bc == UNDEFINED)
			return PointLocUtils.pointLocWRTLineSegment(b.to(c), pb, pc);

		ZDirection ca = crossProductZDirection(pc, pa);
		if (ca == UNDEFINED)
			return PointLocUtils.pointLocWRTLineSegment(c.to(a), pc, pa);

		return ab == bc && bc == ca ? INSIDE : OUTSIDE;
	}

	public static PointLocation pointLocWrtToTriangle(XY a, XY b, XY c, XY p) {
		return pointLocWrtToTriangle(a, b, c, area(a, b, c), p);
	}
}
