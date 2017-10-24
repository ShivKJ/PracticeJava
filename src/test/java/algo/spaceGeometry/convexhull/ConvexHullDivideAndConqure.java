package algo.spaceGeometry.convexhull;

import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import algo.io.IO;
import algo.io.PointGeneration;
import algo.spaceGeometry.Config;
import algo.spaceGeometry.Point;
import algo.spaceGeometry.XY;

public class ConvexHullDivideAndConqure {
	public static void main(String[] args) {
		PointGeneration.setSeed(10L);
		List<Collection<Point>> points = new ArrayList<>();
		int pts = 40000;

		points.add(PointGeneration.pointsOnCircle(new XY(0, 1), 0.5, 0.1, pts));
		points.add(PointGeneration.pointsOnCircle(new XY(-1, 0), 0.5, 0.1, pts));
		points.add(PointGeneration.pointsOnCircle(1, 1.3, 3 * pts));
		points.add(PointGeneration.pointsOnCircle(0, 1, 2 * pts));
		points.add(PointGeneration.pointsOnCircle(new XY(1, 0), 0.5, 0.1, pts));
		points.add(PointGeneration.pointsOnCircle(new XY(0, -1), 0.5, 0.1, pts));
		List<Point> allInputs = points.stream().flatMap(x -> x.stream()).collect(toCollection(LinkedList::new));

		IO.toJson(allInputs, Config.PATH + "a.json");
		long start = currentTimeMillis();
		System.out.println("Starts...");
		//		List<XY> hull = getHullSingle(allInputs);
		List<Point> hull = getHull(points, false);
		IO.toJson(hull, Config.PATH + "b.json");
		System.out.println("Size: " + hull.size());
		System.out.println("time taken: " + (currentTimeMillis() - start));
	}

	public static List<Point> getHull(List<Collection<Point>> inputs, boolean multithread) {
		Stream<Collection<Point>> stream = multithread ? inputs.parallelStream() : inputs.stream();
		return new CHullOptimized<>(stream
				.flatMap(ConvexHullDivideAndConqure::getHull)
				.collect(toCollection(() -> Collections.synchronizedList(new ArrayList<>())))).getConvexHull();
	}

	public static List<Point> getHullSingle(Collection<Point> points) {
		return new CHullOptimized<>(points).getConvexHull();
	}

	public static Stream<Point> getHull(Collection<Point> input) {
		try {
			return new CHullOptimized<>(input).getConvexHull().stream();
		} catch (Exception e) {
			System.out.println("Error");
		}
		return null;
	}
}
