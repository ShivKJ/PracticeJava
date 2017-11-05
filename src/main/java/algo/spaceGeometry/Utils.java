package algo.spaceGeometry;

import static algo.spaceGeometry.Config.TOLERANCE;
import static algo.spaceGeometry.PointUtils.crossProduct;
import static algo.spaceGeometry.PointUtils.line;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.util.Comparator.comparingDouble;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import algo.utils.UtilsAlgo;

public final class Utils {

	private Utils() {}

	public static boolean isZero(double x) {
		return isZero(x, TOLERANCE);
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
		return abs(crossProduct(line(a, b), line(a, c)));
	}

	public static <E extends Point> Optional<E> getFarthestPoint(Collection<? extends E> points, double angle, Comparator<? super E> resolveConflict) {
		double inRad = toRadians(angle);
		return UtilsAlgo.best(points, t -> true, comparingDouble((E p) -> p.X() * cos(inRad) + p.Y() * sin(inRad)).thenComparing(resolveConflict));
	}

	public static <E extends Point> Optional<E> getFarthestPoint(Collection<? extends E> points, double angle) {
		double inRad = toRadians(angle);
		return UtilsAlgo.best(points, t -> true, comparingDouble((Point p) -> p.X() * cos(inRad) + p.Y() * sin(inRad)));
	}

}
