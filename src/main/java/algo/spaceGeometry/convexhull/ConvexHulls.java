package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Utils.getFarthestPoint;
import static algo.spaceGeometry.convexhull.ConvexHullUtils.convexSubHull;
import static algo.spaceGeometry.convexhull.ConvexHullUtils.getPointsOutsideOfCHull;
import static algo.spaceGeometry.convexhull.ConvexHullUtils.kmeans;
import static java.lang.Integer.MAX_VALUE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.ml.clustering.CentroidCluster;

import algo.spaceGeometry.Point;

public final class ConvexHulls {
	private ConvexHulls() {}

	public static <E extends Point> ConvexHull<E> cHullSimple(Collection<? extends E> points) {
		return new CHullJarvisSimple<E>(points).getConvexHull();
	}

	public static <E extends Point> ConvexHull<E> cHull(Collection<? extends E> points) {
		return new CHullJarvisOptimized<E>(points).getConvexHull();
	}

	public static <E extends Point> ConvexHull<E> cHullAngles(Collection<? extends E> points, List<Double> thetas) {
		ConvexHull<E> convexSubHull = convexSubHull(points, thetas);

		convexSubHull.addAll(getPointsOutsideOfCHull(convexSubHull, points));

		return cHull(convexSubHull);
	}

	public static <E extends Point> ConvexHull<E> cHullAngles(Collection<? extends E> points, int noOfAnglesPts) {
		// no of points must be greater than 2.

		double eachAngle = 360 / noOfAnglesPts;

		List<E> convexSubHull = range(0, noOfAnglesPts)
				.mapToObj(i -> getFarthestPoint(points, i * eachAngle).get())
				.collect(toList());

		convexSubHull.add(convexSubHull.get(0));
		convexSubHull.addAll(getPointsOutsideOfCHull(convexSubHull, points));

		return cHull(convexSubHull);
	}

	public static <E extends Point> ConvexHull<E> cHullKmeans(Collection<? extends E> points, int k, int iterations) {

		return cHull(kmeans(points, k, iterations).stream()
				.map(CentroidCluster::getPoints)
				.map(ConvexHulls::cHull)
				.collect(ArrayList<E>::new, List<E>::addAll, List<E>::addAll));
	}

	public static <E extends Point> ConvexHull<E> cHullKmeans(Collection<? extends E> points, int k) {
		return cHullKmeans(points, k, MAX_VALUE);
	}
}
