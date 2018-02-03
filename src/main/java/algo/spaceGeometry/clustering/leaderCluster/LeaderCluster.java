package algo.spaceGeometry.clustering.leaderCluster;

import static algo.spaceGeometry.clustering.leaderCluster.LeaderClusterElements.defaultCriteria;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparingDouble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.Clusterer;
import org.apache.commons.math3.ml.distance.DistanceMeasure;

import algo.spaceGeometry.clustering.WeightedPoint;

public class LeaderCluster<T extends WeightedPoint> extends Clusterer<T> {
	/*
	 * Generic implementation of Leader cluster.
	 * It requires three parameter:
	 * 1) DistanceMeasure: to calculate distance between two spatial point.
	 * 2) Criteria to add point to cluster
	 * 3) Modification done to cluster once a point is chosen to be added to cluster.
	 * 
	 * Steps:
	 * 1) Points are sorted in decreasing order of their weight
	 * 2) A point is added to cluster if it satisfies the criteria. If no such cluster
	 * 	  exists then new cluster is created with this very point. Cluster with largest
	 *    weight is chosen first.
	 * 3) Cluster is modified.
	 */
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

		SortedSet<CentroidCluster<T>> pq = new TreeSet<>(reverseOrder(comparingDouble(LeaderClusterElements::centroidWeight)));

		for (T point : toBeClustered) {
			Iterator<CentroidCluster<T>> iterCluster = pq.iterator();

			while (iterCluster.hasNext()) {
				CentroidCluster<T> cluster = iterCluster.next();

				if (addToCluster.test(cluster, point)) {
					iterCluster.remove();
					clusterUpdator.accept(cluster, point);
					pq.add(cluster);
					break;
				}
			}

			if (pq.isEmpty()) {// no cluster can contain the point so creating new one.
				CentroidCluster<T> newCluster = new CentroidCluster<>(new CentroidPoint(point));
				newCluster.addPoint(point);
				pq.add(newCluster);
			}
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

		public static <T extends WeightedPoint & Clusterable> Builder<T> newInstance() {
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
