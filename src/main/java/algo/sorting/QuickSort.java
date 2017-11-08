package algo.sorting;

import static algo.sorting.Utils.swap;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

public class QuickSort<T extends Comparable<T>> extends ElementSorter<T> {

	public QuickSort(Collection<? extends T> input) {

		super(input);
	}

	public QuickSort(T[] arr) {

		super(arr);
	}

	private int partition(int from, int to) {// p < r
		T pivot = arr[to];

		int pivotIndex = from;

		for (int j = from; j < to; j++)
			if (arr[j].compareTo(pivot) < 0)
				swap(arr, pivotIndex++, j);

		swap(arr, pivotIndex, to);

		return pivotIndex;
	}

	private void quickSort(int from, int to) {
		if (from < to) {
			int pivotIndex = partition(from, to);

			quickSort(from, pivotIndex - 1);
			quickSort(pivotIndex + 1, to);
		}
	}

	@Override
	public List<T> sort() {
		if (isTrivial())
			return emptyList();

		quickSort(0, arr.length - 1);

		return asList(arr);
	}

}
