package algo.spaceGeometry.clustering.leaderCluster;

import org.apache.commons.math3.ml.clustering.Clusterable;

import algo.spaceGeometry.clustering.WeightedPoint;

final class CentroidPoint implements WeightedPoint,Clusterable {
	final double	xy[];
	double			w;

	CentroidPoint(WeightedPoint weightedPoint) {
		this.xy = new double[] { weightedPoint.X(), weightedPoint.Y() };
		this.w = weightedPoint.weight();
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
