package algo.spaceGeometry;

import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import algo.io.IO;
import algo.io.PointGeneration;
import algo.spaceGeometry.convexhull.EmptyCollectionException;
import algo.spaceGeometry.convexhull.preprocessing.PolygonFilteration;

class RectangularFiltering {
	public static void main(String[] args) throws EmptyCollectionException {
		PointGeneration.setSeed(10L);
		List<Collection<XY>> points = new ArrayList<>();
		int pts = 10000;

		points.add(PointGeneration.pointsOnCircle(new XY(0, 1), 0.5, 0.1, pts));
		points.add(PointGeneration.pointsOnCircle(new XY(-1, 0), 0.5, 0.1, pts));
		points.add(PointGeneration.pointsOnCircle(1, 1.3, 3 * pts));
		points.add(PointGeneration.pointsOnCircle(0, 1, 2 * pts));
		points.add(PointGeneration.pointsOnCircle(new XY(1, 0), 0.5, 0.1, pts));
		points.add(PointGeneration.pointsOnCircle(new XY(0, -1), 0.5, 0.1, pts));

		List<XY> allInputs = points.stream().flatMap(x -> x.stream()).collect(toCollection(LinkedList::new));
		double[] angles = new double[] { 0, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330 };
		//		double[] angles = new double[] { 0, 60, 90, 180, 150, 290 };
		PolygonFilteration filteration = new PolygonFilteration(allInputs, angles, 4);

		List<XY> list = filteration.run();
		IO.toJson(list, Config.PATH + "r.json");
		System.out.println(angles.length + " : " + list.size());

	}
}
