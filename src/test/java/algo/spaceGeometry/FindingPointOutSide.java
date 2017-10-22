package algo.spaceGeometry;

import java.util.Collection;
import java.util.List;

import algo.io.IO;
import algo.spaceGeometry.convexhull.ConvexHullUtils;

class FindingPointOutSide {
	public static void main(String[] args) {
		List<XY> points = IO.fromJsonArray(Config.PATH + "input.json", XY.class);
		List<XY> convexHull = IO.fromJsonArray(Config.PATH + "angle.json", XY.class);
		Collection<XY> outside = ConvexHullUtils.getPointsOutsideOfConvexHull(convexHull, points);
		System.out.println(outside.size());
		IO.toJson(outside, Config.PATH + "outside.json");
		
	}
}
