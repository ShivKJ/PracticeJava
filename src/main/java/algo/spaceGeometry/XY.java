package algo.spaceGeometry;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class XY extends AbstractPoint implements Clusterable {

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
	public double[] getPoint() {
		return new double[] { x, y };
	}
}
