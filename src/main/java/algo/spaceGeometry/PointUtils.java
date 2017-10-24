package algo.spaceGeometry;

import static algo.spaceGeometry.Point.ZERO;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

public final class PointUtils {
	private PointUtils() {}

	public final static Point rTheta(double r, double theta) {
		return rTheta(ZERO, r, theta);
	}

	public final static Point rTheta(Point center, double r, double theta) {
		theta = toRadians(theta);
		return new XY(center.X() + r * cos(theta), center.Y() + r * sin(theta));
	}

	public static Point to(Point a, Point b) {
		return new XY(b.X() - a.X(), b.Y() - a.Y());
	}

	public static Point unitVector(Point point) {
		double mag = point.magnitude();
		return new XY(point.X() / mag, point.Y() / mag);
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
}
