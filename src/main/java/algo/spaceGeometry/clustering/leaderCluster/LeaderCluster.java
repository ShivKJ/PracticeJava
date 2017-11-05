package algo.spaceGeometry.clustering.leaderCluster;

import static algo.spaceGeometry.clustering.leaderCluster.LeaderClusterElements.defaultCriteria;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingDouble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterer;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

import algo.spaceGeometry.clustering.WeightedPoint;

public class LeaderCluster<T extends WeightedPoint> extends Clusterer<T> {
	private final BiPredicate<CentroidCluster<? extends T>, T>	addToCluster;
	private final BiConsumer<CentroidCluster<? extends T>, T>	clusterUpdator;

	private LeaderCluster(DistanceMeasure distanceMeasure,
			BiPredicate<CentroidCluster<? extends T>, T> addToCluster,
			BiConsumer<CentroidCluster<? extends T>, T> clusterUpdator) {

		super(distanceMeasure);
		this.addToCluster = addToCluster;
		this.clusterUpdator = clusterUpdator;
	}

	@Override
	public List<Cluster<T>> cluster(Collection<T> points) {
		List<T> toBeClustered = new ArrayList<>(points);
		toBeClustered.sort(reverseOrder(comparingDouble(T::weight)));

		Queue<CentroidCluster<T>> pq = new PriorityQueue<>(reverseOrder(comparingDouble(LeaderClusterElements::centroidWeight)));

		for (T point : toBeClustered) {
			Collection<CentroidCluster<T>> bucket = new LinkedList<>();

			while (!pq.isEmpty()) {
				CentroidCluster<T> cluster = pq.remove();

				if (addToCluster.test(cluster, point)) {
					clusterUpdator.accept(cluster, point);
					cluster.addPoint(point);
					pq.add(cluster);
					break;
				}

				bucket.add(cluster);
			}

			if (pq.isEmpty()) {
				CentroidCluster<T> newCluster = new CentroidCluster<>(new CentroidPoint(point));
				newCluster.addPoint(point);
				pq.add(newCluster);
			}

			pq.addAll(bucket);

		}
		
		return new ArrayList<>(pq);
	}

	public static class Builder<T extends WeightedPoint> {
		private double	radius			= Double.MAX_VALUE , maxWeight = radius;
		private int		maxClusterSize	= Integer.MAX_VALUE;

		private BiPredicate<CentroidCluster<? extends T>, T>	addToCluster;
		private BiConsumer<CentroidCluster<? extends T>, T>		clusterUpdator;
		private DistanceMeasure									distanceMeasure;

		private Builder() {}

		public static <T extends WeightedPoint> Builder<T> newInstance() {
			return new Builder<>();
		}

		public Builder<T> setMaxRadius(double radius) {
			this.radius = radius;
			return this;
		}

		public Builder<T> setMaxWeight(double maxWeight) {
			this.maxWeight = maxWeight;
			return this;
		}

		public Builder<T> setMaxClusterSize(int maxClusterSize) {
			this.maxClusterSize = maxClusterSize;
			return this;
		}

		@SuppressWarnings("unchecked")
		public Builder<T> setClusterUpdator(BiConsumer<? super CentroidCluster<? extends T>, ? super T> clusterUpdator) {
			this.clusterUpdator = (BiConsumer<CentroidCluster<? extends T>, T>) clusterUpdator;
			return this;
		}

		@SuppressWarnings("unchecked")
		public Builder<T> setCriteriaToAddToCluster(BiPredicate<? super CentroidCluster<? extends T>, ? super T> addToCluster) {
			this.addToCluster = (BiPredicate<CentroidCluster<? extends T>, T>) addToCluster;
			return this;
		}

		public Builder<T> setDistanceMeasure(DistanceMeasure distanceMeasure) {
			this.distanceMeasure = distanceMeasure;
			return this;
		}

		public LeaderCluster<T> build() {
			if (clusterUpdator == null)
				clusterUpdator = LeaderClusterElements::updateCluster;

			BiPredicate<CentroidCluster<? extends T>, T> addingCriteria = defaultCriteria(distanceMeasure, radius, maxClusterSize, maxWeight);

			return new LeaderCluster<>(distanceMeasure,
					addToCluster == null ? addingCriteria : addingCriteria.and(addToCluster),
					clusterUpdator);
		}
	}

}
