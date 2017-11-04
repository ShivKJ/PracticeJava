package algo.spaceGeometry.clustering;

import static java.lang.Double.compare;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingDouble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.BiPredicate;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterer;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

public class LeaderCluster<T extends WeightedPoint> extends Clusterer<T> {
	private final List<T>													toBeClustered;
	private final Queue<CentroidCluster<CentroidPoint>>						pq;
	private final BiPredicate<CentroidCluster<? extends WeightedPoint>, T>	addToCluster;

	public LeaderCluster(Collection<? extends T> points, DistanceMeasure distanceMeasure, double radius) {
		this(points, distanceMeasure, radius, Integer.MAX_VALUE);
	}

	public LeaderCluster(Collection<? extends T> points, DistanceMeasure distanceMeasure, double radius, int pointCount) {
		this(points, distanceMeasure, radius, pointCount, Double.MAX_VALUE);
	}

	public LeaderCluster(Collection<? extends T> points, DistanceMeasure distanceMeasure, double radius, int pointCount, double maxWeight) {
		this(points, distanceMeasure, (cluster, p) -> cluster.getPoints().size() < pointCount &&
				((WeightedPoint) cluster.getCenter()).weight() + p.weight() <= maxWeight &&
				distanceMeasure.compute(cluster.getCenter().getPoint(), p.getPoint()) <= radius);
	}

	public LeaderCluster(Collection<? extends T> points,
			DistanceMeasure distanceMeasure,
			BiPredicate<CentroidCluster<? extends WeightedPoint>, T> addToCluster) {

		super(distanceMeasure);

		this.toBeClustered = new ArrayList<>(points);
		this.toBeClustered.sort(reverseOrder(comparingDouble(T::weight)));

		this.pq = new PriorityQueue<>();

		this.addToCluster = (cluster, p) -> addToCluster.test(cluster, p);
	}

	private static class CentroidPoint implements WeightedPoint, Comparable<CentroidPoint> {
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

		@Override
		public int compareTo(CentroidPoint o) {

			return compare(o.w, w);
		}

	}

	private List<CentroidCluster<T>> output() {

		List<CentroidCluster<T>> output = new ArrayList<>(pq.size());

		while (!pq.isEmpty()) {
			CentroidCluster<CentroidPoint> cluster = pq.poll();

			CentroidCluster<T> centroidCluster = new CentroidCluster<>(cluster.getCenter());
			cluster.getPoints().forEach(cluster::addPoint);

			output.add(centroidCluster);
		}

		return output;
	}

	@Override
	public List<? extends Cluster<T>> cluster(Collection<T> points) {
		for (T point : toBeClustered) {
			Collection<CentroidCluster<CentroidPoint>> bucket = new ArrayList<>(pq.size());

			while (!pq.isEmpty()) {
				CentroidCluster<CentroidPoint> cluster = pq.poll();

				if (addToCluster.test(cluster, point)) {
					updateCluster(cluster, point);
					break;
				}

				bucket.add(cluster);
			}

			if (pq.isEmpty())
				pq.offer(new CentroidCluster<>(new CentroidPoint(point.X(), point.Y(), point.weight())));

			pq.addAll(bucket);
		}

		return output();
	}

	private static <T extends WeightedPoint> void updateCluster(CentroidCluster<CentroidPoint> cluster, T point) {
		CentroidPoint centroidPoint = (CentroidPoint) cluster.getCenter();

		double wCluster = centroidPoint.w , w = point.weight();
		double newW = wCluster + w;

		centroidPoint.setX((centroidPoint.X() * wCluster + point.X() * w) / newW);
		centroidPoint.setY((centroidPoint.Y() * wCluster + point.Y() * w) / newW);
		centroidPoint.w = newW;

	}

}
