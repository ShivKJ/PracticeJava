package algo.sorting;

import static java.lang.System.arraycopy;
import static java.lang.reflect.Array.newInstance;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class MergeSort<T extends Comparable<T>> implements Sort<T> {
	@SuppressWarnings("rawtypes")
	private final static Comparator<Comparable> NULL_LAST = nullsLast(naturalOrder());

	private final T[] arr;

	public MergeSort(Collection<? extends T> input) {
		this.arr = input.isEmpty() ? null : input.toArray(get(input.iterator().next(), input.size()));
	}

	public MergeSort(T[] input) {
		this.arr = input.clone();
	}

	@Override
	public List<T> sort() {
		if (arr == null)
			return emptyList();

		mergeSort(0, arr.length - 1);

		return asList(this.arr);
	}

	private void mergeSort(int from, int to) {
		if (from < to) {
			int mid = (from + to) / 2;

			mergeSort(from, mid);
			mergeSort(mid + 1, to);

			mergeSorted(from, mid, to);
		}
	}

	private void mergeSorted(int from, int mid, int to) {
		int leftSize = mid - from + 1 , rightSize = to - mid;

		T[] left = get(leftSize + 1) , right = get(rightSize + 1);

		arraycopy(arr, from, left, 0, leftSize);
		arraycopy(arr, mid + 1, right, 0, rightSize);

		for (int indx = from, l = 0, r = 0; indx <= to; indx++)
			arr[indx] = NULL_LAST.compare(left[l], right[r]) < 0 ? left[l++] : right[r++];

	}

	private T[] get(int size) {
		return get(arr[0], size);
	}

	@SuppressWarnings("unchecked")
	private static <T> T[] get(T t, int size) {
		return (T[]) newInstance(t.getClass(), size);
	}
}
