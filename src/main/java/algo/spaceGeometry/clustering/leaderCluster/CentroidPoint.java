package algo.spaceGeometry.clustering.leaderCluster;

import algo.spaceGeometry.clustering.WeightedPoint;

final class CentroidPoint implements WeightedPoint {
	final double	xy[];
	double			w;

	public CentroidPoint(double x, double y, double w) {
		this.xy = new double[] { x, y };
		this.w = w;
	}

	@Override
	public double X() {

		return xy[0];
	}

	@Override
	public double Y() {

		return xy[1];
	}

	void setX(double x) {
		xy[0] = x;
	}

	void setY(double y) {
		xy[1] = y;
	}

	@Override
	public double weight() {

		return w;
	}

	@Override
	public double[] getPoint() {

		return xy;
	}

}