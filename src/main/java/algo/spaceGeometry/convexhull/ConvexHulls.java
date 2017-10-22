package algo.spaceGeometry.convexhull;

import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

import algo.spaceGeometry.XY;

public final class ConvexHulls {
	private ConvexHulls() {}

	public static List<XY> convexHull(Collection<? extends XY> points) {
		try {
			return new ConvexHullJarvisSimple(points).getConvexHull();
		} catch (EmptyCollectionException e) {
			e.printStackTrace();
			return emptyList();
		}
	}

	public static List<XY> convexHullOptimized(Collection<? extends XY> points) {
		try {
			return new ConvexHullJarvisOptimised(points).getConvexHull();
		} catch (EmptyCollectionException e) {
			e.printStackTrace();
			return emptyList();
		}
	}
}
