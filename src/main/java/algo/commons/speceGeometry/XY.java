package algo.commons.speceGeometry;

import static algo.commons.speceGeometry.Utils.isEqual;

public class XY implements Coordinate {
	protected final double x , y;

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
	public double Z() {
		throw new UnsupportedOperationException("This is XY Geometry.");
	}

}
