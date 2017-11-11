package algo.sorting;

import static algo.sorting.Utils.makeArray;
import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Comparators.isInOrder;
import static java.util.Arrays.asList;
import static java.util.Arrays.binarySearch;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BucketSort<T extends Comparable<T>> extends ElementSorter<T> {
	private final List<T>	bkt;
	private T[]				bucket;

	@SuppressWarnings("unchecked")
	public BucketSort(Collection<? extends T> input, List<? extends T> buket) {
		super(input);
		this.bkt = unmodifiableList((List<T>) buket);

	}

	public BucketSort(T[] arr, T[] bucket) {
		super(arr);
		this.bkt = unmodifiableList(asList(bucket));
	}

	private void initializeBucket() {
		if (!isInOrder(bkt, T::compareTo) || bkt.size() <= 1)
			throw new IllegalArgumentException();

		this.bucket = makeArray(bkt.get(0), bkt.size());
		int i = 0;
		for (T e : bkt)
			this.bucket[i++] = e;

	}

	private int indx(T e) {
		int indx = binarySearch(bucket, e);

		return indx < 0 ? -indx - 1 : indx;
	}

	public T[][] getBuckets() {
		initializeBucket();

		int bucketSize = bucket.length;
		int[] counts = new int[bucketSize];

		for (T e : arr)
			counts[indx(e)]++;

		T[][] output = makeArray(arr, bucketSize);

		for (int i = 0; i < bucketSize; i++)
			output[i] = makeArray(arr[0], counts[i]);

		int[] indx = new int[bucketSize];

		for (T e : arr) {
			int currIndex = indx(e);
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
