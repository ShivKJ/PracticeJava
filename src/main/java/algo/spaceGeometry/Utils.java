package algo.spaceGeometry;

import static java.lang.Math.abs;

import java.util.List;

public class Utils {

	private Utils() {}

	public static boolean isEqual(double x1, double x2, double tol) {
		return abs(x1 - x2) <= tol;
	}

	public static boolean isPointInsideConvexHull(List<XY> convexHull, XY point) {
		return true;
	}

}
