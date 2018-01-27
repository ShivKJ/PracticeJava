package algo.sorting;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

public class InsertionSort<T extends Comparable<T>> extends ElementSorter<T> {
	/*
	 * In this sorting algorithm, we insert element at right place.
	 */
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

			// for all elements at less than jth index which are greater than jthElement
			// are shifted one toward jth element.
			while (i >= 0 && arr[i].compareTo(jth) > 0)
				arr[i + 1] = arr[i--];

			// in the end, there is a void created at (i+1)th index to be filled with jth element. 
			arr[i + 1] = jth;
		}

		return asList(arr);
	}

}
