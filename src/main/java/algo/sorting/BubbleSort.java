package algo.sorting;

import static algo.sorting.Utils.swap;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

public class BubbleSort<T extends Comparable<T>> extends ElementSorter<T> {

	public BubbleSort(Collection<? extends T> input) {

		super(input);
	}

	public BubbleSort(T[] input) {
		super(input);
	}

	@Override
	public List<T> sort() {
		if (isTrivial())
			return emptyList();

		int lastSecElemIndex = arr.length - 2;

		for (int i = 0; i <= lastSecElemIndex; i++)
			for (int j = lastSecElemIndex; j >= i; j--)
				if (arr[j].compareTo(arr[j + 1]) > 0)
					swap(arr, j, j + 1);

		return asList(arr);
	}

}
