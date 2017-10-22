package algo.spaceGeometry;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import algo.io.IO;
import algo.io.PointGeneration;
import algo.spaceGeometry.convexhull.ConvexHullJarvisOptimised;
import algo.spaceGeometry.convexhull.EmptyCollectionException;

public class DemoConvecHullJarvisOptimized {
	public static void main(String[] args) throws EmptyCollectionException {
		Collection<XY> points = new LinkedList<>();
		points.addAll(PointGeneration.pointsOnCircle(1, 1.1, 1000));
		XY a = new XY(0, 0) , b = new XY(2, 0) , c = new XY(2, 0.5) , d = new XY(2, .5);
		points.addAll(PointGeneration.pointsOnPolygon(asList(a, b, c, d), 0.1, 200));
		IO.toJson(points, Config.PATH + "a.json");
		List<XY> hull = new ConvexHullJarvisOptimised(points).getConvexHull();
		IO.toJson(hull, Config.PATH + "b.json");
		System.out.println("size: " + hull.size());

	}
}
