package algo.spaceGeometry;

import static algo.spaceGeometry.Utils.isEqual;
import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class XY implements Clusterable {
	protected final double	x , y;
	public static final XY	E1	= new XY(1, 0) , E2 = new XY(0, 1);

	public XY(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public final static XY rTheta(double r, double theta) {
		theta = toRadians(theta);
		return new XY(r * cos(theta), r * sin(theta));
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

		long temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(y);
		return prime * result + (int) (temp ^ temp >>> 32);
	}

	public double dotProduct(XY coords2) {
		double res = X() * coords2.X() + Y() * coords2.Y();

		return res;
	}

	public double magnitude() {
		return hypot(X(), Y());

	}

	public XY to(XY b) {
		double x = b.X() - X() , y = b.Y() - Y();

		return new XY(x, y);
	}

	public XY unitVector() {
		double mag = magnitude();
		return new XY(X() / mag, Y() / mag);
	}

	@Override
	public double[] getPoint() {

		return new double[] { x, y };
	}

}
