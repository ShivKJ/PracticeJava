package algo.commons.speceGeometry;

import static java.lang.Math.abs;

public class Utils {
	private Utils() {}

	public static boolean isEqual(double x1, double x2, double tol) {
		return abs(x1 - x2) <= tol;
	}

	public static double dotProduct(Coordinate coords1, Coordinate coords2) {
		double res = coords1.X() * coords2.X() + coords1.Y() * coords2.Y();

		try {
			res += coords1.Z() * coords2.Z();
		} catch (UnsupportedOperationException e) {}

		return res;
	}

	public static XYZ crossProduct(Coordinate coord1, Coordinate coord2) {
		double z = coord1.X() * coord2.Y() - coord2.X() * coord1.Y() , x = 0 , y = 0;

		try {
			double z1 = coord1.Z() , z2 = coord2.Z();

			x = coord1.Y() * z2 - coord2.Y() * z1;
			y = z1 * coord2.X() - z2 * coord1.X();

		} catch (UnsupportedOperationException e) {}

		return new XYZ(x, y, z);
	}
}
