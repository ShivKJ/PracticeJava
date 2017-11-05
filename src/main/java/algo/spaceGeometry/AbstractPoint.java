package algo.spaceGeometry;

import static algo.spaceGeometry.Utils.isEqual;
import static java.lang.Double.doubleToLongBits;

public abstract class AbstractPoint implements Point {
	@Override
	public String toString() {
		return "[" + X() + ", " + Y() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Point
				&& isEqual(((Point) obj).X(), X())
				&& isEqual(((Point) obj).Y(), Y());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		long temp = doubleToLongBits(X());
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = doubleToLongBits(Y());
		return prime * result + (int) (temp ^ temp >>> 32);
	}

}
