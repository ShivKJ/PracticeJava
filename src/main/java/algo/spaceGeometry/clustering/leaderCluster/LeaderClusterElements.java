package algo.spaceGeometry.clustering.leaderCluster;

import java.util.function.BiPredicate;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

import algo.spaceGeometry.clustering.WeightedPoint;

class LeaderClusterElements {
	private LeaderClusterElements() {}

	static <T extends WeightedPoint> BiPredicate<CentroidCluster<? extends T>, T> defaultCriteria(DistanceMeasure distanceMeasure, double radius,
		int maxClusterSize, double maxWeight) {
		return (cluster, point) -> cluster.getPoints().size() < maxClusterSize
			&& centroidWeight(cluster) + point.weight() <= maxWeight
			&& inRadius(distanceMeasure, radius, cluster, point);
	}

	static double centroidWeight(CentroidCluster<? extends WeightedPoint> cluster) {
		return ((WeightedPoint) cluster.getCenter()).weight();
	}

	static <T extends WeightedPoint> boolean inRadius(DistanceMeasure distanceMeasure, double radius, CentroidCluster<? extends T> cluster, T point) {
		CentroidPoint centroidPoint = (CentroidPoint) cluster.getCenter();

		double wCluster = centroidPoint.w , w = point.weight();
		double newW = wCluster + w;
		double[] newCoords = new double[centroidPoint.xy.length] , pt = point.getPoint();

		for (int i = 0; i < newCoords.length; i++)
			newCoords[i] = (centroidPoint.xy[i] * wCluster + pt[i] * w) / newW;

		if (distanceMeasure.compute(pt, newCoords) > radius)
			return false;

		for (T p : cluster.getPoints())
			if (distanceMeasure.compute(p.getPoint(), newCoords) > radius)
				return false;

		return true;
	}

	static <T extends WeightedPoint> void updateCluster(CentroidCluster<? extends T> cluster, T point) {
		CentroidPoint centroidPoint = (CentroidPoint) cluster.getCenter();

		double wCluster = centroidPoint.w , w = point.weight();
		double newW = wCluster + w;
		double[] xy = centroidPoint.xy , pt = point.getPoint();

		for (int i = 0; i < xy.length; i++)
			xy[i] = (xy[i] * wCluster + pt[i] * w) / newW;

		centroidPoint.w = newW;

	}

}
