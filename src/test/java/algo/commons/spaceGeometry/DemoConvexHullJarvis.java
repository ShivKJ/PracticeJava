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
		Set<XY> points = generate(() -> new XY(10 * random.nextGaussian(), 10 * random.nextGaussian()))
				.limit(200)
				.collect(toSet());

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
