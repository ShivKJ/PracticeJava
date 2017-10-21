package algo.spaceGeometry;

import static algo.spaceGeometry.Coordinate.TOLERANCE;
import static algo.spaceGeometry.ZDirection.DOWN;
import static algo.spaceGeometry.ZDirection.UNDEFINED;
import static algo.spaceGeometry.ZDirection.UP;
import static java.lang.Math.abs;

import java.util.Iterator;
import java.util.List;

public class Utils {

	private Utils() {}

	public static boolean isEqual(double x1, double x2, double tol) {
		return abs(x1 - x2) <= tol;
	}

	public static boolean isEqual(double x1, double x2) {
		return isEqual(x1, x2, TOLERANCE);
	}

	public static boolean pointInConvexHull(List<XY> convexHull, XY p) {
		/*
		 * Each point in convex hull should be distinct.
		 */
		if (convexHull.isEmpty())
			return false;

		int size = convexHull.size();

		Iterator<XY> iter = convexHull.iterator();

		XY a = iter.next();

		if (size == 1)
			return a.equals(p);

		XY b = iter.next() , ab = a.to(b) , pa = p.to(a) , pb = p.to(b);

		ZDirection direction = crossProductZDirection(pa, pb);

		if (direction == UNDEFINED && isPointOnLineSegment(ab, pa, pb))
			return true;

		if (size == 2)
			return false;

		for (; iter.hasNext();) {
			XY x = iter.next() , px = p.to(x);

			ZDirection bCrossX = crossProductZDirection(pb, px);

			if (bCrossX == UNDEFINED)
				return isPointOnLineSegment(b.to(x), pb, px);
			else if (direction != bCrossX)
				return false;

			b = x;
			pb = px;
		}
		return true;
	}

	public static boolean pointInOrOnTriangle(XY a, XY b, XY c, XY p) {
		XY pa = p.to(a) , pb = p.to(b) , pc = p.to(c);

		ZDirection bc = crossProductZDirection(pb, pc);

		return crossProductZDirection(pa, pb) == bc && bc == crossProductZDirection(pc, pa);
	}

	private static ZDirection crossProductZDirection(XY a, XY b) {
		double left = a.X() * b.Y() , right = a.Y() * b.X();

		if (isEqual(left, right))
			return UNDEFINED;

		return left > right ? UP : DOWN;
	}

	private static boolean isPointOnLineSegment(XY ab, XY pa, XY pb) {
		return isEqual(abs(ab.x), abs(pa.x) + abs(pb.x)) && isEqual(abs(ab.y), abs(pa.y) + abs(pb.y));
	}

}
