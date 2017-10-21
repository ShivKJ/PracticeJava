package algo.spaceGeometry;

import static algo.spaceGeometry.Config.TOLERANCE;
import static algo.spaceGeometry.PointLocation.INSIDE;
import static algo.spaceGeometry.PointLocation.ON;
import static algo.spaceGeometry.PointLocation.OUTSIDE;
import static algo.spaceGeometry.ZDirection.DOWN;
import static algo.spaceGeometry.ZDirection.UNDEFINED;
import static algo.spaceGeometry.ZDirection.UP;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import java.util.Iterator;
import java.util.List;

public final class Utils {

	private Utils() {}

	public static boolean isEqual(double x1, double x2, double tol) {
		return abs(x1 - x2) <= tol;
	}

	public static boolean isEqual(double x1, double x2) {
		return isEqual(x1, x2, TOLERANCE);
	}

	public static PointLocation pointWrtConvexHull(List<XY> convexHull, XY p) {
		/*
		 * Each point in convex hull should be distinct and on line segment formed by any two point does not contain a
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

		if (size == 2)
			return OUTSIDE;

		for (; iter.hasNext();) {
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

	public static PointLocation pointLocWrtToTriangle(XY a, XY b, XY c, XY p) {
		XY pa = p.to(a) , pb = p.to(b) , pc = p.to(c);

		ZDirection ab = crossProductZDirection(pa, pb);
		ZDirection bc = crossProductZDirection(pb, pc);
		ZDirection ca = crossProductZDirection(pc, pa);

		if (ab == UNDEFINED && bc == UNDEFINED && ca == UNDEFINED) {
			double AB = a.to(b).magnitude() , BC = b.to(c).magnitude() , CA = c.to(a).magnitude();
			if (isEqual(AB, BC + CA))
				return pointLocWRTLineSegment(a.to(b), pa, pb);
			if (isEqual(BC, CA + AB))
				return pointLocWRTLineSegment(b.to(c), pb, pc);

			return pointLocWRTLineSegment(c.to(a), pc, pa);
		}

		if (ab == UNDEFINED)
			return pointLocWRTLineSegment(a.to(b), pa, pb);

		if (bc == UNDEFINED)
			return pointLocWRTLineSegment(b.to(c), pb, pc);

		if (ca == UNDEFINED)
			return pointLocWRTLineSegment(c.to(a), pc, pa);

		return ab == bc && bc == ca ? INSIDE : OUTSIDE;
	}

	private static ZDirection crossProductZDirection(XY a, XY b) {
		double left = a.X() * b.Y() , right = a.Y() * b.X();

		if (isEqual(left, right))
			return UNDEFINED;

		return left > right ? UP : DOWN;
	}

	private static PointLocation pointLocWRTLineSegment(XY ab, XY pa, XY pb) {
		return isEqual(abs(ab.x), abs(pa.x) + abs(pb.x)) && isEqual(abs(ab.y), abs(pa.y) + abs(pb.y)) ? ON : OUTSIDE;
	}

	public static XY rotate(XY vector, double theta) {
		theta = toRadians(theta);
		double x = vector.X() , y = vector.Y();
		return new XY(x * cos(theta) - y * sin(theta), x * sin(theta) + y * cos(theta));
	}
}
