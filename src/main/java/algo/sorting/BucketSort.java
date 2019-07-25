package algo.sorting;

import static algo.sorting.Utils.makeArray;
import static com.google.common.base.Predicates.notNull;
import static com.google.common.collect.Comparators.isInOrder;
import static java.lang.Math.max;
import static java.util.Arrays.asList;
import static java.util.Arrays.binarySearch;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BucketSort<T extends Comparable<T>>extends ElementSorter<T> {
    /*
     * Input elements are divided into bucket. For example: given bucket = [1   5   9]
     * 						-10, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
     * 				
     *   bucket[-INF -> 4  ]    -10  0  1  2  3  4
     *   bucket[   5 -> 8  ]      5  6  7  8
     *   bucket[   9 -> INF]      9  10 11
     *   
     *   Then we sort individual bucket before merging them into single bucket.
     */
    private final List<T> bkt;
    private T[]           bucket;	// used to binary search element to find bucket index.

    @SuppressWarnings("unchecked")
    public BucketSort(Collection<? extends T> input, List<? extends T> sortedbuket) {
        super(input);
        this.bkt = unmodifiableList((List<T>) sortedbuket);

    }

    public BucketSort(T[] arr, T[] bucket) {
        super(arr);
        this.bkt = unmodifiableList(asList(bucket));
    }

    private void initializeBucket() {
        if (!isInOrder(bkt, T::compareTo))
            ;

        this.bucket = makeArray(bkt.get(0), bkt.size());
        int i = 0;
        for (T e : bkt)
            this.bucket[i++] = e;

    }

    private int indx(T e) {
        int indx = binarySearch(bucket, e);

        return max(indx < 0 ? -indx - 2 : indx, 0);
        // taking max, to put elements less than min(bucket) to lowest bucket.
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
        for (int i = 0; i < output.length; i++)
            System.out.println(bucket[i] + " : " + Arrays.toString(output[i]));
        return output;
    }

    @Override
    public List<T> sort() {
        if (isTrivial())
            return emptyList();

        T[][] output = getBuckets();

        // each bucket is parallely.
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
