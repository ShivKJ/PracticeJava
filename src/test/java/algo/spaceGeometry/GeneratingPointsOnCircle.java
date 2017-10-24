package algo.spaceGeometry;

import static algo.spaceGeometry.convexhull.ConvexHulls.cHull;

import java.util.Collection;
import java.util.List;

import algo.io.IO;
import algo.io.PointGeneration;

class GeneratingPointsOnCircle {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Collection<XY> points = PointGeneration.pointsOnCircle(0, 1, 50000);
		IO.toJson(points, Config.PATH + "a.json");
		List<XY> cHull = cHull(points);
		IO.toJson(cHull, Config.PATH + "b.json");
		System.out.println(cHull.size());
		System.out.println(System.currentTimeMillis() - start);
	}
}
