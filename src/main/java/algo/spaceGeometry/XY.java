package algo.spaceGeometry;

import static algo.spaceGeometry.Utils.isEqual;

public class XY implements Coordinate {
	protected final double	x , y;
	public static final XY	E1	= new XY(1, 0) , E2 = new XY(0, 1);

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
				&& isEqual(((XY) obj).x, x, TOLERANCE)
				&& isEqual(((XY) obj).y, y, TOLERANCE);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		long temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(y);
		return prime * result + (int) (temp ^ temp >>> 32);
	}

	@Override
	public double Z() {
		throw new UnsupportedOperationException("This is XY Geometry.");
	}
}
