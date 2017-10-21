package algo.io;

import static java.nio.file.Paths.get;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

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

	public static <T> T fromJson(Path filePath, Class<T> clazz) {
		try (Reader reader = new FileReader(filePath.toFile())) {
			return GSON.fromJson(reader, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static <T> T fromJson(String filePath, Class<T> clazz) {
		return fromJson(get(filePath), clazz);
	}

	public static <T> List<T> fromJsonArray(Path filePath, Class<T> elemClazz) {
		try (Reader reader = new FileReader(filePath.toFile())) {
			return GSON.fromJson(reader, new ListType<>(elemClazz));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> fromJsonArray(String filePath, Class<T> elemClazz) {
		return fromJsonArray(get(filePath), elemClazz);
	}

	private final static class ListType<T> implements ParameterizedType {
		private final Class<T> wrapped;

		ListType(Class<T> wrapper) {
			this.wrapped = wrapper;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return new Type[] { wrapped };
		}

		@Override
		public Type getRawType() {
			return List.class;
		}

		@Override
		public Type getOwnerType() {
			return null;
		}
	}
}
