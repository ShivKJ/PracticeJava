package algo.point2d;

import static algo.point2d.Point.ZERO;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;
import static java.util.Comparator.comparingDouble;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public final class Utils {
	public static final double	TOLERANCE		= 10e-8;
	public static final double	EARTH_RADIUS	= 6371e3;

	private Utils() {}

	public final static Point rTheta(double r, double theta) {
		return rTheta(ZERO, r, theta);
	}

	public final static Point rTheta(Point center, double r, double theta) {
		theta = toRadians(theta);
		return new XY(center.X() + r * cos(theta), center.Y() + r * sin(theta));
	}

	public static Point line(Point from, Point to) {
		return new XY(to.X() - from.X(), to.Y() - from.Y());
	}

	public static double magnitude(Point point) {
		return hypot(point.X(), point.Y());
	}

	public static Point unitVector(Point point) {
		double mag = point.magnitude();
		return new XY(point.X() / mag, point.Y() / mag);
	}

	public static double distanceBetween(Point from, Point to) {
		return hypot(from.X() - to.X(), from.Y() - to.Y());
	}

	public static Point rotate(Point point, double theta) {
		return new XY(point.X() * cos(theta) - point.Y() * sin(theta), point.X() * sin(theta) + point.Y() * cos(theta));
	}

	public static double dotProduct(Point a, Point b) {
		return a.X() * b.X() + a.Y() * b.Y();
	}

	public static double crossProduct(Point a, Point b) {
		return a.X() * b.Y() - a.Y() * b.X();
	}

	public static boolean isZero(double x) {
		return Utils.isZero(x, TOLERANCE);
	}

	public static <E> Optional<E> best(Collection<? extends E> points, Predicate<? super E> filteration, Comparator<? super E> comp) {
		E best = null;

		for (E tmp : points)
			if (filteration.test(tmp))
				if (best == null)
					best = tmp;
				else if (comp.compare(tmp, best) > 0)
					best = tmp;

		return ofNullable(best);
	}

	public static boolean isZero(double x, double tol) {
		return abs(x) <= tol;
	}

	public static boolean isEqual(double x1, double x2, double tol) {
		return isZero(x1 - x2, tol);
	}

	public static boolean isEqual(double x1, double x2) {
		return isEqual(x1, x2, TOLERANCE);
	}

	public static Point linearCombination(double a, Point A, double b, Point B) {
		return new XY(a * A.X() + b * B.X(), a * A.Y() + b * B.Y());
	}

	public static double area(Point a, Point b, Point c) {
		return abs(crossProduct(line(a, b), line(a, c))) / 2;
	}

	public static <E extends Point> Optional<E> getFarthestPoint(Collection<? extends E> points, double angle, Comparator<? super E> resolveConflict) {
		double inRad = toRadians(angle);
		return best(points, t -> true, comparingDouble((E p) -> p.X() * cos(inRad) + p.Y() * sin(inRad)).thenComparing(resolveConflict));
	}

	public static <E extends Point> Optional<E> getFarthestPoint(Collection<? extends E> points, double angle) {
		double inRad = toRadians(angle);
		return best(points, t -> true, comparingDouble((Point p) -> p.X() * cos(inRad) + p.Y() * sin(inRad)));
	}

	public static <E> Optional<E> first(Collection<? extends E> points, Predicate<? super E> filteration) {
		for (E e : points)
			if (filteration.test(e))
				return of(e);

		return empty();

	}

	public static double haversineDistance(double[] from, double[] to) {

		double fromLat = toRadians(from[0]) , toLat = toRadians(to[0]) , deltaLng = toRadians(to[1] - from[1]);
		double a = pow(sin((toLat - fromLat) / 2), 2) + cos(fromLat) * cos(toLat) * pow(sin(deltaLng / 2), 2);

		return EARTH_RADIUS * 2 * atan2(sqrt(a), sqrt(1 - a));
	}
}
