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

import algo.spaceGeometry.Boundary;
import algo.spaceGeometry.XY;

public final class ConvexHullUtils {
	private ConvexHullUtils() {}

	//--------------------------------------------------------------------------------------
	private static <E extends XY> Collection<E> getPointsOutsideOfCHull(Boundary<? extends E> convexHull, Stream<? extends E> pointStream) {
		return pointStream.filter(x -> pointWrtCHull(convexHull, x) == OUTSIDE).collect(toList());
	}

	public static <E extends XY> Collection<E> getPointsOutsideOfCHull(Boundary<? extends E> convexHull, Collection<? extends E> points) {
		return getPointsOutsideOfCHull(convexHull, points.stream());
	}

	public static <E extends XY> Collection<E> getPointsOutsideOfCHullConcurrent(Boundary<? extends E> convexHull, Collection<? extends E> points) {
		return getPointsOutsideOfCHull(convexHull, points.parallelStream());
	}

	//--------------------------------------------------------------------------------------
	private static <E extends XY> List<Boundary<? extends XY>> cHulls(Stream<Collection<? extends E>> clusterStream) {
		return clusterStream.map(ConvexHulls::cHull).collect(toList());
	}

	public static <E extends XY> List<Boundary<? extends XY>> cHulls(List<Collection<? extends E>> clusters) {
		return cHulls(clusters.stream());
	}

	public static <E extends XY> List<Boundary<? extends XY>> cHullsConcurrent(List<Collection<? extends E>> clusters) {
		return cHulls(clusters.parallelStream());
	}

	//--------------------------------------------------------------------------------------

	private static <E extends XY> Boundary<E> mergeCHulls(Stream<Collection<? extends E>> clusterStream) {
		return cHull(clusterStream.map(ConvexHulls::cHull)
				.flatMap(Boundary::stream)
				.collect(toList()));
	}

	public static <E extends XY> Boundary<E> mergeCHulls(Collection<Collection<? extends E>> clusters) {
		return mergeCHulls(clusters.stream());
	}

	public static <E extends XY> Boundary<E> mergeCHullsConcurrent(Collection<Collection<? extends E>> clusters) {
		return mergeCHulls(clusters.parallelStream());
	}

	//--------------------------------------------------------------------------------------

	public static <T extends Clusterable> List<CentroidCluster<T>> kmeans(Collection<T> points, int k, int iterations) {
		return new KMeansPlusPlusClusterer<T>(k, iterations).cluster(points);
	}

	//--------------------------------------------------------------------------------------

	private static <E extends XY> Boundary<E> convexSubHull(Collection<? extends E> points, Stream<Double> thetas) {

		return cHull(thetas.map(d -> getFarthestPoint(points, d).get()).collect(toList()));
	}

	public static <E extends XY> Boundary<E> convexSubHull(Collection<? extends E> points, List<Double> thetas) {
		return convexSubHull(points, thetas.stream());
	}

	public static <E extends XY> Boundary<E> convexSubHullConcurrent(Collection<? extends E> points, List<Double> thetas) {
		return convexSubHull(points, thetas.parallelStream());
	}

	public static <E extends XY> double areaOfConvexHull(List<? extends E> convexHull) {
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
