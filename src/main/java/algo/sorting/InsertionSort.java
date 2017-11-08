package algo.sorting;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

public class InsertionSort<T extends Comparable<T>> extends ElementSorter<T> {

	public InsertionSort(Collection<? extends T> data) {
		super(data);
	}

	public InsertionSort(T[] data) {
		super(data);
	}

	@Override
	public List<T> sort() {
		if (isTrivial())
			return emptyList();

		for (int j = 1; j < arr.length; j++) {
			T jth = arr[j];
			int i = j - 1;

			while (i >= 0 && arr[i].compareTo(jth) > 0)
				arr[i + 1] = arr[i--];

			arr[i + 1] = jth;
		}

		return asList(arr);
	}

}
