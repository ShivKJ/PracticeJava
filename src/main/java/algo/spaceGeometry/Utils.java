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

	public static boolean isZero(double x) {
		return isEqual(x, 0);
	}

	public static boolean isEqual(double x1, double x2) {
		return isEqual(x1, x2, TOLERANCE);
	}

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

	public static ZDirection crossProductZDirection(XY a, XY b) {
		double crossProduct = crossProduct(a, b);

		if (isZero(crossProduct))
			return UNDEFINED;

		return crossProduct > 0 ? UP : DOWN;
	}

	public static double crossProduct(XY a, XY b) {
		return a.X() * b.Y() - a.Y() * b.X();
	}

	public static PointLocation pointLocWRTLineSegment(XY ab, XY pa, XY pb) {
		return isEqual(abs(ab.x), abs(pa.x) + abs(pb.x)) && isEqual(abs(ab.y), abs(pa.y) + abs(pb.y)) ? ON : OUTSIDE;
	}

	public static XY rotate(XY vector, double theta) {
		theta = toRadians(theta);
		double x = vector.X() , y = vector.Y();
		return new XY(x * cos(theta) - y * sin(theta), x * sin(theta) + y * cos(theta));
	}

	public static XY linearCombination(double a, XY A, double b, XY B) {
		return new XY(a * A.X() + b * B.X(), a * A.Y() + b * B.Y());
	}
}
