package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Utils.area;
import static algo.spaceGeometry.Utils.getFarthestPoint;
import static algo.spaceGeometry.convexhull.ConvexHulls.convexHullOptimized;
import static algo.spaceGeometry.pointLocation.PointLocUtils.pointWrtConvexHull;
import static algo.spaceGeometry.pointLocation.PointLocation.OUTSIDE;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.Clusterable;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;

import algo.spaceGeometry.XY;

public final class ConvexHullUtils {
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

	public static double areaOfConvexHull(List<? extends XY> convexHull) {
		/*
		 * Convex hull having no point, one point or two points is defined to have area zero.
		 */
		if (convexHull.size() <= 2)
			return 0;

		Iterator<? extends XY> iter = convexHull.iterator();

		XY o = iter.next() , a = iter.next();

		double area = 0;

		while (iter.hasNext())
			area += area(o, a, a = iter.next());

		return area;
	}
}
