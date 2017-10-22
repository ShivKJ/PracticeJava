package algo.spaceGeometry;

import java.util.List;

import algo.io.IO;
import algo.spaceGeometry.pointLocation.PointLocUtils;

public class PointInConvexHull {
	public static void main(String[] args) {

		List<XY> list = IO.fromJsonArray(Config.PATH + "b.json", XY.class);

		System.out.println(list);
		System.out.println(PointLocUtils.pointWrtConvexHull(list, new XY(0, 0)));
		System.out.println(PointLocUtils.pointWrtConvexHull(list, new XY(-426, -58)));
		System.out.println(PointLocUtils.pointWrtConvexHull(list, new XY(22, 417)));
		System.out.println(PointLocUtils.pointWrtConvexHull(list, new XY(124, 58)));
		System.out.println(PointLocUtils.pointWrtConvexHull(list, new XY(23, 421)));
	}
}
