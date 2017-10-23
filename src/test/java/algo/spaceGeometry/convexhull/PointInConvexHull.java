package algo.spaceGeometry.convexhull;

import algo.io.IO;
import algo.spaceGeometry.Boundary;
import algo.spaceGeometry.Config;
import algo.spaceGeometry.XY;
import algo.spaceGeometry.pointLocation.Locations;

public class PointInConvexHull {
	public static void main(String[] args) {

		Boundary<XY> list = new ConvexHull<>(IO.fromJsonArray(Config.PATH + "b.json", XY.class));

		System.out.println(list);
		System.out.println(Locations.pointWrtCHull(list, new XY(0, 0)));
		System.out.println(Locations.pointWrtCHull(list, new XY(-426, -58)));
		System.out.println(Locations.pointWrtCHull(list, new XY(22, 417)));
		System.out.println(Locations.pointWrtCHull(list, new XY(124, 58)));
		System.out.println(Locations.pointWrtCHull(list, new XY(23, 421)));
	}
}
