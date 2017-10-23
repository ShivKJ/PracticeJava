package algo.spaceGeometry;

import static algo.spaceGeometry.Config.TOLERANCE;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static java.util.Comparator.comparingDouble;
import static java.util.Optional.ofNullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

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

	public static double crossProduct(XY a, XY b) {
		return a.X() * b.Y() - a.Y() * b.X();
	}

	public static XY rotate(XY vector, double theta) {
		theta = toRadians(theta);

		double x = vector.X() , y = vector.Y();

		return new XY(x * cos(theta) - y * sin(theta), x * sin(theta) + y * cos(theta));
	}

	public static XY linearCombination(double a, XY A, double b, XY B) {
		return new XY(a * A.X() + b * B.X(), a * A.Y() + b * B.Y());
	}

	public static double area(XY a, XY b, XY c) {
		return abs(crossProduct(a.to(b), a.to(c)));
	}

	public static Optional<XY> bestPoint(Collection<? extends XY> points, Predicate<? super XY> filteration, Comparator<? super XY> comp) {
		XY best = null;

		for (XY tmp : points)
			if (filteration.test(tmp))
				if (best == null)
					best = tmp;
				else if (comp.compare(tmp, best) > 0)
					best = tmp;

		return ofNullable(best);
	}

	public static Optional<XY> getFarthestPoint(Collection<? extends XY> points, double angle, Comparator<? super XY> resolveConflict) {
		return bestPoint(points, t -> true, comparingDouble((XY p) -> p.X() * cos(angle) + p.Y() * sin(angle)).thenComparing(resolveConflict));
	}

	public static Optional<XY> getFarthestPoint(Collection<? extends XY> points, double angle) {
		return bestPoint(points, t -> true, comparingDouble((XY p) -> p.X() * cos(angle) + p.Y() * sin(angle)));
	}

}
