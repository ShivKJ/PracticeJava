package algo.spaceGeometry.convexhull;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import algo.io.IO;
import algo.spaceGeometry.Config;
import algo.spaceGeometry.XY;

public class DemoConvexHullJarvis1 {
	static Random random = new Random();

	public static double getNext(double mean, double std) {
		//						return mean + std * random.nextGaussian();
		return (int) (mean + std * random.nextGaussian());
		//		return Math.min((int) (mean + std * random.nextGaussian()), 4);
	}

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		double std = 100 , mean = 0;
		Supplier<XY> supplier = () -> new XY(getNext(mean, std), getNext(mean, std));

		Collection<XY> points = generate(supplier)
				.limit(100_000)
				.collect(toList());
		//				points.add(new XY(0, 0));
		//				points.add(new XY(10, 10));
		//				points.add(new XY(10, -5));
		//				points.add(new XY(10, -1));

		ConvexHull convexHullJarvis = new ConvexHullJarvisOptimised(points);
		//		ConvexHull convexHullJarvis = new ConvexHullJarvisSimple(points);

		String inputPath = Config.PATH + "a.json";
		String outputPath = Config.PATH + "b.json";
		IO.toJson(points, inputPath);

		List<XY> convexHull = convexHullJarvis.getConvexHull();
		IO.toJson(convexHull, outputPath);

		System.out.println("convexhull size: " + convexHull.size());

		System.out.println(System.currentTimeMillis() - start);
	}
}
