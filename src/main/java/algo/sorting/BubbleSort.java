package algo.sorting;

import static algo.sorting.Utils.swap;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import java.util.Collection;
import java.util.List;

public class BubbleSort<T extends Comparable<T>>extends ElementSorter<T> {
    /*
     * One of the sorting technique in which larger element is pushed
     * one side of array.
     * 
     *                 12        10       8      3
     *                 
     *     Step 1:     12        10       3      8
     *                 12         3       10     8
     *                 3         12       10     8
     *     
     *     Step 2:     3         12        8     10
     *                 3          8       12     10
     *                 3          8       10     12
     *     
     *  In every step, on small element is pushed to correct sorted position. 
     */
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
