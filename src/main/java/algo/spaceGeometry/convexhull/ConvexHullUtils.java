package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Utils.getFarthestPoint;
import static algo.spaceGeometry.convexhull.ConvexHulls.convexHullOptimized;
import static algo.spaceGeometry.pointLocation.PointLocUtils.pointWrtConvexHull;
import static algo.spaceGeometry.pointLocation.PointLocation.OUTSIDE;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;

import algo.spaceGeometry.XY;

public class ConvexHullUtils {
	private ConvexHullUtils() {}

	//--------------------------------------------------------------------------------------
	private static Collection<XY> getPointsOutsideOfConvexHull(List<? extends XY> convexHull, Stream<? extends XY> pointStream) {
		return pointStream.filter(x -> pointWrtConvexHull(convexHull, x) == OUTSIDE).collect(toList());
	}

	public static Collection<XY> getPointsOutsideOfConvexHull(List<? extends XY> convexHull, Collection<? extends XY> points) {
		return getPointsOutsideOfConvexHull(convexHull, points.stream());
	}

	public static Collection<XY> getPointsOutsideOfConvexHullConcurrent(List<? extends XY> convexHull, Collection<? extends XY> points) {
		return getPointsOutsideOfConvexHull(convexHull, points.parallelStream());
	}

	//--------------------------------------------------------------------------------------
	private static List<List<XY>> convexHulls(Stream<Collection<? extends XY>> clusterStream) {
		return clusterStream.map(ConvexHulls::convexHullOptimized).collect(toList());
	}

	public static List<List<XY>> convexHulls(List<Collection<? extends XY>> clusters) {
		return convexHulls(clusters.stream());
	}

	public static List<List<XY>> convexHullsConcurrent(List<Collection<? extends XY>> clusters) {
		return convexHulls(clusters.parallelStream());
	}

	//--------------------------------------------------------------------------------------

	private static List<XY> mergeConvexHulls(Stream<Collection<? extends XY>> clusterStream) {
		return convexHullOptimized(clusterStream.map(ConvexHulls::convexHullOptimized)
				.flatMap(List::stream)
				.collect(toList()));
	}

	public static List<XY> mergeConvexHulls(List<Collection<? extends XY>> clusters) {
		return mergeConvexHulls(clusters.stream());
	}

	public static List<XY> mergeConvexHullsConcurrent(List<Collection<? extends XY>> clusters) {
		return mergeConvexHulls(clusters.parallelStream());
	}

	//--------------------------------------------------------------------------------------

	public static <T extends Clusterable> List<CentroidCluster<T>> kmeans(Collection<T> points, int k, int iterations) {
		return new KMeansPlusPlusClusterer<T>(k, iterations).cluster(points);
	}

	//--------------------------------------------------------------------------------------

	private static List<XY> convexSubHull(List<? extends XY> points, Stream<Double> thetas) {
		if (points.isEmpty())
			return emptyList();

		return convexHullOptimized(thetas.map(d -> getFarthestPoint(points, d).get()).collect(toList()));
	}

	public static List<XY> convexSubHull(List<? extends XY> points, List<Double> thetas) {
		return convexSubHull(points, thetas.stream());
	}

	public static List<XY> convexSubHullConcurrent(List<? extends XY> points, List<Double> thetas) {
		return convexSubHull(points, thetas.parallelStream());
	}

	public static List<XY> convexHull(List<? extends XY> points, List<Double> thetas) {
		List<XY> convexSubHull = convexSubHull(points, thetas);

		convexSubHull.addAll(getPointsOutsideOfConvexHull(convexSubHull, points));

		return convexHullOptimized(convexSubHull);
	}
}
