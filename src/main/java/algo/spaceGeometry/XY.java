package algo.spaceGeometry;

import static algo.spaceGeometry.Utils.isEqual;
import static java.lang.Double.doubleToLongBits;

public class XY implements Point {

	private final double x , y;

	public XY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double X() {
		return x;
	}

	@Override
	public double Y() {
		return y;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof XY
				&& isEqual(((XY) obj).x, x)
				&& isEqual(((XY) obj).y, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		long temp = doubleToLongBits(x);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = doubleToLongBits(y);
		return prime * result + (int) (temp ^ temp >>> 32);
	}
}
