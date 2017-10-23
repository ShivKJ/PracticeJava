package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Utils.area;
import static algo.spaceGeometry.Utils.getFarthestPoint;
import static algo.spaceGeometry.convexhull.ConvexHulls.cHull;
import static algo.spaceGeometry.pointLocation.Locations.pointWrtCHull;
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
	private static Collection<XY> getPointsOutsideOfCHull(List<? extends XY> convexHull, Stream<? extends XY> pointStream) {
		return pointStream.filter(x -> pointWrtCHull(convexHull, x) == OUTSIDE).collect(toList());
	}

	public static Collection<XY> getPointsOutsideOfCHull(List<? extends XY> convexHull, Collection<? extends XY> points) {
		return getPointsOutsideOfCHull(convexHull, points.stream());
	}

	public static Collection<XY> getPointsOutsideOfCHullConcurrent(List<? extends XY> convexHull, Collection<? extends XY> points) {
		return getPointsOutsideOfCHull(convexHull, points.parallelStream());
	}

	//--------------------------------------------------------------------------------------
	private static List<List<XY>> cHulls(Stream<Collection<? extends XY>> clusterStream) {
		return clusterStream.map(ConvexHulls::cHull).collect(toList());
	}

	public static List<List<XY>> cHulls(List<Collection<? extends XY>> clusters) {
		return cHulls(clusters.stream());
	}

	public static List<List<XY>> cHullsConcurrent(List<Collection<? extends XY>> clusters) {
		return cHulls(clusters.parallelStream());
	}

	//--------------------------------------------------------------------------------------

	private static List<XY> mergeCHulls(Stream<Collection<? extends XY>> clusterStream) {
		return cHull(clusterStream.map(ConvexHulls::cHull)
				.flatMap(List<XY>::stream)
				.collect(toList()));
	}

	public static List<XY> mergeCHulls(List<Collection<? extends XY>> clusters) {
		return mergeCHulls(clusters.stream());
	}

	public static List<XY> mergeCHullsConcurrent(List<Collection<? extends XY>> clusters) {
		return mergeCHulls(clusters.parallelStream());
	}

	//--------------------------------------------------------------------------------------

	public static <T extends Clusterable> List<CentroidCluster<T>> kmeans(Collection<T> points, int k, int iterations) {
		return new KMeansPlusPlusClusterer<T>(k, iterations).cluster(points);
	}

	//--------------------------------------------------------------------------------------

	private static List<XY> convexSubHull(Collection<? extends XY> points, Stream<Double> thetas) {

		return cHull(thetas.map(d -> getFarthestPoint(points, d).get()).collect(toList()));
	}

	public static List<XY> convexSubHull(Collection<? extends XY> points, List<Double> thetas) {
		return convexSubHull(points, thetas.stream());
	}

	public static List<XY> convexSubHullConcurrent(Collection<? extends XY> points, List<Double> thetas) {
		return convexSubHull(points, thetas.parallelStream());
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
