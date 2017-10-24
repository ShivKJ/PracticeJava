package algo.spaceGeometry.convexhull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import algo.io.IO;
import algo.io.PointGeneration;
import algo.spaceGeometry.Config;
import algo.spaceGeometry.Point;

public class DemoConvecHullJarvisOptimized {
	public static void main(String[] args) {

		PointGeneration.setSeed(10L);
		Collection<Point> points = new LinkedList<>();
		points.addAll(PointGeneration.pointsOnCircle(1, 1.1, 1_000));
		//		XY a = new XY(0, 0) , b = new XY(2, 0) , c = new XY(2, 0.5) , d = new XY(2, .5);
		//		points.addAll(PointGeneration.pointsOnPolygon(asList(a, b, c, a), 0.1, 200));
		IO.toJson(points, Config.PATH + "a.json");
		long start = System.currentTimeMillis();
		List<Point> hull = new CHullOptimized<>(points).getConvexHull();
		IO.toJson(hull, Config.PATH + "b.json");
		System.out.println("size: " + hull.size());
		System.out.println(System.currentTimeMillis() - start);
	}
}
