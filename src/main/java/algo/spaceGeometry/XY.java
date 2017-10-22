package algo.spaceGeometry;

import static algo.spaceGeometry.Utils.isEqual;
import static java.lang.Double.doubleToLongBits;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class XY implements Clusterable {
	public static final XY E1 = new XY(1, 0) , E2 = new XY(0, 1) , ZERO = new XY(0, 0);

	protected final double x , y;

	public XY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public final static XY rTheta(double r, double theta) {
		return rTheta(ZERO, r, theta);
	}

	public final static XY rTheta(XY center, double r, double theta) {
		theta = toRadians(theta);
		return new XY(center.x + r * cos(theta), center.y + r * sin(theta));
	}

	public double X() {
		return x;
	}

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

	public double dotProduct(XY b) {
		return x * b.x + y * b.y;

	}

	public double magnitude() {
		return hypot(x, y);
	}

	public double distanceTo(XY to) {
		return hypot(x - to.x, y - to.y);
	}

	public XY to(XY b) {
		return new XY(b.x - x, b.y - y);
	}

	public XY unitVector() {
		double mag = magnitude();
		return new XY(x / mag, y / mag);
	}

	@Override
	public double[] getPoint() {

		return new double[] { x, y };
	}

}
