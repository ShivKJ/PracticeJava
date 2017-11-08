package algo.sorting;

import static algo.sorting.Utils.makeArray;

import java.util.Collection;

public abstract class ElementSorter<T extends Comparable<T>> implements Sort<T> {
	protected final T[] arr;

	public ElementSorter(Collection<? extends T> input) {
		this.arr = input.isEmpty() ? null : input.toArray(makeArray(input.iterator().next(), input.size()));
	}

	public ElementSorter(T[] arr) {
		this.arr = arr.clone();
	}

	protected boolean isTrivial() {
		return arr == null || arr.length == 0;
	}

}
