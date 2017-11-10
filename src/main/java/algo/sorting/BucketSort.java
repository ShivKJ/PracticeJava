package algo.sorting;

import static algo.sorting.Utils.makeArray;
import static com.google.common.base.Predicates.notNull;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;

public class BucketSort<T extends Comparable<T>> extends ElementSorter<T> {
	private final ToIntFunction<T>	hasher;
	private final int				noBucket;

	public BucketSort(Collection<? extends T> input, ToDoubleFunction<T> hasher, int noBucket) {
		super(input);
		this.hasher = e -> (int) (hasher.applyAsDouble(e) * noBucket);
		this.noBucket = noBucket;
	}

	public BucketSort(T[] arr, ToDoubleFunction<T> hasher, int noBucket) {
		super(arr);

		this.hasher = e -> (int) (hasher.applyAsDouble(e) * noBucket);
		this.noBucket = noBucket;
	}

	public T[][] getBuckets() {
		int[] counts = new int[noBucket + 1];

		for (T e : arr)
			counts[hasher.applyAsInt(e)]++;

		T[][] output = makeArray(arr, noBucket + 1);

		for (int i = 0; i < noBucket + 1; i++)
			output[i] = makeArray(arr[0], counts[i]);

		int[] indx = new int[noBucket + 1];

		for (T e : arr) {
			int currIndex = hasher.applyAsInt(e);
			output[currIndex][indx[currIndex]++] = e;
		}

		return output;
	}

	@Override
	public List<T> sort() {
		if (isTrivial())
			return emptyList();

		T[][] output = getBuckets();

		stream(output).filter(notNull())
				.parallel()
				.forEach(Arrays::sort);

		int indx = 0;

		for (T[] es : output)
			for (T e : es)
				arr[indx++] = e;

		return asList(arr);
	}

}
