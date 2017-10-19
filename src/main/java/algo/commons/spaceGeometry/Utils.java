package algo.commons.spaceGeometry;

import static java.lang.Math.abs;

public class Utils {

	private Utils() {}

	public static boolean isEqual(double x1, double x2, double tol) {
		return abs(x1 - x2) <= tol;
	}

}
