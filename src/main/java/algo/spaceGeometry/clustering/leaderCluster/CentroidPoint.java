package algo.spaceGeometry.clustering.leaderCluster;

import org.apache.commons.math3.ml.clustering.Clusterable;

import algo.spaceGeometry.clustering.WeightedPoint;

final class CentroidPoint implements WeightedPoint, Clusterable {
	final double	xy[];
	double			w;

	<T extends WeightedPoint & Clusterable> CentroidPoint(T weightedPoint) {
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

}
