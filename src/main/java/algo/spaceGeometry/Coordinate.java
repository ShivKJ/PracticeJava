package algo.spaceGeometry;

import static java.lang.Math.hypot;
import static java.lang.Math.sqrt;

public interface Coordinate {
	double TOLERANCE = 10e-8;

	double X();

	double Y();

	double Z();

	public default double dotProduct( Coordinate coords2) {
		double res = X() * coords2.X() + Y() * coords2.Y();

		try {
			res += Z() * coords2.Z();
		} catch (UnsupportedOperationException e) {}

		return res;
	}

	public default XYZ crossProduct(Coordinate coord2) {
		double z = X() * coord2.Y() - coord2.X() * Y() , x = 0 , y = 0;
		double z1 , z2 = z1 = 0;

		try {
			z1 = Z();
		} catch (UnsupportedOperationException e) {}

		try {
			z2 = coord2.Z();
		} catch (UnsupportedOperationException e) {}

		x = Y() * z2 - coord2.Y() * z1;
		y = z1 * coord2.X() - z2 * X();

		return new XYZ(x, y, z);
	}

	public default double magnitude() {
		if (this instanceof XY)
			return hypot(X(), Y());

		return sqrt(X() * X() + Y() * Y() + Z() * Z());
	}

	public default XYZ to(Coordinate b) {
		double x = b.X() - X() , y = b.Y() - Y() , z1 = 0 , z2 = z1;

		try {
			z1 = Z();
		} catch (UnsupportedOperationException e) {}
		try {
			z2 = b.Z();
		} catch (UnsupportedOperationException e) {}

		return new XYZ(x, y, z2 - z1);
	}
}
