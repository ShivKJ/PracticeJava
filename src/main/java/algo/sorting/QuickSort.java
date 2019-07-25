package algo.sorting;

import static algo.sorting.Utils.swap;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

public class QuickSort<T extends Comparable<T>>extends ElementSorter<T> {

    public QuickSort(Collection<? extends T> input) {

        super(input);
    }

    public QuickSort(T[] arr) {

        super(arr);
    }

    private int partition(int from, int to) {// p < r
        T pivot = arr[to];// pivoting last element.

        int pivotIndex = from;// final index of pivot element.

        for (int j = from; j < to; j++)
            if (arr[j].compareTo(pivot) < 0)
                swap(arr, pivotIndex++, j);
        // if jth element is less than pivot element, then swapping with element at pivotIndex.
        // In this way all element less than pivot element are at index less than pivotIndex.

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
