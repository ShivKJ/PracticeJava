package algo.commons.spaceGeometry;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.generate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

public class DemoConvexHullJarvis {
	public static void main(String[] args) throws IOException {
		Random random = new Random();
		double std = 1 , mean = 3;
		Set<XY> points = generate(() -> new XY(mean + std * random.nextGaussian(), mean + std * random.nextGaussian()))
				.limit(200)
				.collect(toSet());
		points.add(new XY(0, 0));

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
