package algo.spaceGeometry;

import java.util.Collection;
import java.util.List;

import algo.io.IO;
import algo.io.PointGeneration;
import algo.spaceGeometry.convexhull.CHullAlgo;
import algo.spaceGeometry.convexhull.CHullOptimized;

class GeneratingPointsOnCircle {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Collection<XY> points = PointGeneration.pointsOnCircle(0, 1, 50000);
		IO.toJson(points, Config.PATH + "a.json");
		//		ConvexHull convexHull = new ConvexHullJarvisSimple(points);
		CHullAlgo<XY> convexHull = new CHullOptimized<>(points);
		List<XY> cHull = convexHull.getConvexHull();
		IO.toJson(cHull, Config.PATH + "b.json");
		System.out.println(cHull.size());
		System.out.println(System.currentTimeMillis() - start);
	}
}
