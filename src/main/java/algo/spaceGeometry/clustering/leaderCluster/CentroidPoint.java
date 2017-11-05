package algo.spaceGeometry.clustering.leaderCluster;

import algo.spaceGeometry.clustering.WeightedPoint;

final class CentroidPoint implements WeightedPoint {
	final double	xy[];
	double			w;

	CentroidPoint(WeightedPoint weightedPoint) {
		this.xy = weightedPoint.getPoint().clone();
		this.w = weightedPoint.weight();
	}

	@Override
	public double weight() {

		return w;
	}

	@Override
	public double[] getPoint() {

		return xy;
	}

	@Override
	public double X() {

		return xy[0];
	}

	@Override
	public double Y() {

		return xy[1];
	}

}
