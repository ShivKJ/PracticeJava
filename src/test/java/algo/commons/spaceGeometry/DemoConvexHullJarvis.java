package algo.commons.spaceGeometry;

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

public class DemoConvexHullJarvis {
	static Random random = new Random();

	public static double getNext(double mean, double std) {
		return mean + std * random.nextGaussian();
		//				return (int) (mean + std * random.nextGaussian());
		//		return Math.min((int) (mean + std * random.nextGaussian()), 4);
	}

	public static void main(String[] args) throws IOException {
		double std = 4 , mean = 0;
		Supplier<XY> supplier = () -> new XY(getNext(mean, std), getNext(mean, std));

		Collection<XY> points = generate(supplier)
				.limit(10000)
				.collect(toList());
		//		points.add(new XY(0, 0));
		//		points.add(new XY(10, 10));
		//		points.add(new XY(10, -5));
		//		points.add(new XY(10, -1));

		ConvexHullJarvis convexHullJarvis = new ConvexHullJarvis(points);

		Gson gson = new Gson();

		JsonWriter jsonWriter = new JsonWriter(new FileWriter("a.json"));
		gson.toJson(points, points.getClass(), jsonWriter);
		jsonWriter.close();

		jsonWriter = new JsonWriter(new FileWriter("b.json"));
		gson.toJson(convexHullJarvis.getHull(), List.class, jsonWriter);
		jsonWriter.close();;
	}
}
