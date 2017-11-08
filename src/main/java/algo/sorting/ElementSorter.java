package algo.sorting;

import static java.lang.reflect.Array.newInstance;

import java.util.Collection;

public abstract class ElementSorter<T extends Comparable<T>> implements Sort<T> {
	protected final T[] arr;

	public ElementSorter(Collection<? extends T> input) {
		this.arr = input.isEmpty() ? null : input.toArray(get(input.iterator().next(), input.size()));
	}

	public ElementSorter(T[] arr) {
		this.arr = arr.clone();
	}

	protected boolean isTrivial() {
		return arr == null || arr.length == 0;
	}

	@SuppressWarnings("unchecked")
	protected static <T> T[] get(T t, int size) {
		return (T[]) newInstance(t.getClass(), size);
	}
}
