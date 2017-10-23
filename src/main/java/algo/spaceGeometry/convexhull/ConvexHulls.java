package algo.spaceGeometry.convexhull;

import java.util.Collection;
import java.util.List;

import algo.spaceGeometry.XY;

public final class ConvexHulls {
	private ConvexHulls() {}

	public static List<XY> convexHull(Collection<? extends XY> points) {
		return new ConvexHullJarvisSimple(points).getConvexHull();
	}

	public static List<XY> convexHullOptimized(Collection<? extends XY> points) {
		return new ConvexHullJarvisOptimised(points).getConvexHull();
	}
}
