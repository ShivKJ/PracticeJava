package algo.spaceGeometry;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import algo.spaceGeometry.convexhull.ConvexHull;
import algo.spaceGeometry.convexhull.ConvexHullJarvisSimple;
import algo.spaceGeometry.convexhull.EmptyCollectionException;

public class DemoConvexHullJarvis {
	static Random random = new Random(10L);

	public static double getNext(double mean, double std) {
		//				return mean + std * random.nextGaussian();
		//		return (int) (mean + std * random.nextGaussian());
		return Math.min((int) (mean + std * random.nextGaussian()), 4);
	}

	public static void main(String[] args) throws IOException, EmptyCollectionException {
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

		//		ConvexHull convexHullJarvis = new ConvexHullJarvisOptimised(points);
		ConvexHull convexHullJarvis = new ConvexHullJarvisSimple(points);

		Gson gson = new Gson();
		String path = "";
		String inputPath = path + "a.json";
		String outputPath = path + "b.json";
		JsonWriter jsonWriter = new JsonWriter(new FileWriter(inputPath));
		gson.toJson(points, points.getClass(), jsonWriter);
		jsonWriter.close();

		jsonWriter = new JsonWriter(new FileWriter(outputPath));
		List<XY> convexHull = convexHullJarvis.getConvexHull();
		gson.toJson(convexHull, List.class, jsonWriter);
		jsonWriter.close();
		System.out.println("convexhull size: " + convexHull.size());

		System.out.println(System.currentTimeMillis() - start);
	}
}
