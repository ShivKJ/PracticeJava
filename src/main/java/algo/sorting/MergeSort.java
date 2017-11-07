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
	private final Collection<T>	input;
	private T[]					arr;
	private Comparator<T>		nullLast;

	@SuppressWarnings("unchecked")
	public MergeSort(Collection<? extends T> data) {
		this.input = (Collection<T>) data;
	}

	@Override
	public List<T> sort() {
		if (input.isEmpty())
			return emptyList();

		int size = input.size();

		this.arr = input.toArray(get(input.iterator().next(), size));
		this.nullLast = nullsLast(naturalOrder());

		mergeSort(0, size - 1);

		return asList(this.arr);
	}

	private void mergeSort(int p, int r) {
		if (p < r) {
			int q = (p + r) / 2;

			mergeSort(p, q);
			mergeSort(q + 1, r);

			merge(p, q, r);
		}
	}

	private void merge(int p, int q, int r) {
		int n1 = q - p + 1 , n2 = r - q;

		T[] L = get(n1 + 1) , R = get(n2 + 1);

		arraycopy(arr, p, L, 0, n1);
		arraycopy(arr, q + 1, R, 0, n2);

		for (int k = p, i = 0, j = 0; k <= r; k++)
			arr[k] = nullLast.compare(L[i], R[j]) < 0 ? L[i++] : R[j++];

	}

	@SuppressWarnings("unchecked")
	private static <T> T[] get(T t, int size) {
		return (T[]) newInstance(t.getClass(), size);
	}

	private T[] get(int size) {
		return get(arr[0], size);
	}
}
