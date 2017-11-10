package algo.sorting;

import static algo.sorting.Utils.makeArray;
import static java.lang.System.arraycopy;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsLast;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class MergeSort<T extends Comparable<T>> extends ElementSorter<T> {
	@SuppressWarnings("rawtypes")
	private final static Comparator<Comparable> NULL_LAST = nullsLast(naturalOrder());

	public MergeSort(Collection<? extends T> input) {
		super(input);
	}

	public MergeSort(T[] input) {
		super(input);
	}

	@Override
	public List<T> sort() {
		if (isTrivial())
			return emptyList();

		mergeSort(0, arr.length - 1);

		return asList(this.arr);
	}

	private void mergeSort(int from, int to) {
		if (from < to) {
			int mid = from + to >> 1;

			mergeSort(from, mid);
			mergeSort(mid + 1, to);

			mergeSorted(from, mid, to);
		}
	}

	private void mergeSorted(int from, int mid, int to) {
		int leftSize = mid - from + 1 , rightSize = to - mid;

		T[] left = createArr(leftSize + 1) , right = createArr(rightSize + 1);

		arraycopy(arr, from, left, 0, leftSize);
		arraycopy(arr, mid + 1, right, 0, rightSize);

		for (int indx = from, l = 0, r = 0; indx <= to; indx++)
			arr[indx] = NULL_LAST.compare(left[l], right[r]) < 0 ? left[l++] : right[r++];

	}

	private T[] createArr(int size) {
		return makeArray(arr[0], size);
	}

}
