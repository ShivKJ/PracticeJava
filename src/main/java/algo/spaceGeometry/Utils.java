package algo.spaceGeometry;

import static algo.spaceGeometry.Config.TOLERANCE;
import static algo.spaceGeometry.ZDirection.DOWN;
import static algo.spaceGeometry.ZDirection.UNDEFINED;
import static algo.spaceGeometry.ZDirection.UP;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public final class Utils {

	private Utils() {}

	public static boolean isEqual(double x1, double x2, double tol) {
		return abs(x1 - x2) <= tol;
	}

	public static boolean isEqual(double x1, double x2) {
		return isEqual(x1, x2, TOLERANCE);
	}

	public static boolean isZero(double x) {
		return abs(x) <= TOLERANCE;
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
}
