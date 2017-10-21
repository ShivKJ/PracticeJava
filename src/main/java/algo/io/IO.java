package algo.io;

import static java.nio.file.Paths.get;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import algo.spaceGeometry.XY;

public final class IO {
	private final static Gson GSON = new Gson();

	public static void toJson(Collection<? extends XY> points, Path jsonPath) {
		try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(jsonPath.toFile()))) {
			GSON.toJson(points, points.getClass(), jsonWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void toJson(Collection<? extends XY> points, String jsonPath) {
		toJson(points, get(jsonPath));
	}
}
