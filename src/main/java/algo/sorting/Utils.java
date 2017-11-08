package algo.sorting;

import static java.lang.reflect.Array.newInstance;

public class Utils {

	private Utils() {}

	public static <T> void swap(T[] arr, int i, int j) {
		if (i != j) {
			T ith = arr[i];
			arr[i] = arr[j];
			arr[j] = ith;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] makeArray(T t, int size) {
		return (T[]) newInstance(t.getClass(), size);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] makeArray(T t, int... size) {
		return (T[]) newInstance(t.getClass(), size);
	}
}
