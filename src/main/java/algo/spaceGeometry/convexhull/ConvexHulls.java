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

import algo.spaceGeometry.XY;

public final class ConvexHulls {
	private ConvexHulls() {}

	public static List<XY> cHullSimple(Collection<? extends XY> points) {
		return new ConvexHullJarvisSimple(points).getConvexHull();
	}

	public static List<XY> cHull(Collection<? extends XY> points) {
		return new ConvexHullJarvisOptimised(points).getConvexHull();
	}

	public static List<XY> cHullAngles(Collection<? extends XY> points, List<Double> thetas) {
		List<XY> convexSubHull = convexSubHull(points, thetas);

		convexSubHull.addAll(getPointsOutsideOfCHull(convexSubHull, points));

		return cHull(convexSubHull);
	}

	public static List<XY> cHullAngles(Collection<? extends XY> points, int noOfAnglesPts) {
		double eachAngle = 360 / noOfAnglesPts;

		List<XY> convexSubHull = range(0, noOfAnglesPts)
				.mapToObj(i -> getFarthestPoint(points, i * eachAngle).get())
				.collect(toList());

		convexSubHull.addAll(getPointsOutsideOfCHull(convexSubHull, points));

		return cHull(convexSubHull);
	}

	public static List<XY> cHullKmeans(Collection<? extends XY> points, int k, int iterations) {

		return cHull(kmeans(points, k, iterations).stream()
				.map(CentroidCluster::getPoints)
				.map(ConvexHulls::cHull)
				.collect(ArrayList<XY>::new, List<XY>::addAll, List<XY>::addAll));
	}

	public static List<XY> cHullKmeans(Collection<? extends XY> points, int k) {
		return cHullKmeans(points, k, MAX_VALUE);
	}
}
