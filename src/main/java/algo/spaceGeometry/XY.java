package algo.spaceGeometry;

import org.apache.commons.math3.ml.clustering.Clusterable;

public class XY extends AbstractPoint implements Clusterable {

	private final double[] xy;

	public XY(double x, double y) {
		this.xy = new double[] { x, y };
	}

	@Override
	public double X() {
		return xy[0];
	}

	@Override
	public double Y() {
		return xy[1];
	}

	@Override
	public double[] getPoint() {
		return xy.clone();
	}
}
