package algo.dyamic;

import static java.lang.Math.max;
import static java.util.Arrays.sort;
import static java.util.Comparator.comparingInt;

import java.util.LinkedList;
import java.util.List;

public class KnapSack01 {
    public static class KnapSackItem {
        final int weight, value;

        public KnapSackItem(int weight, int value) {
            this.weight = weight;
            this.value = value;
        }

        public int weight() {
            return weight;
        }

        public int value() {
            return value;
        }

        @Override
        public String toString() {
            return "Weight: " + weight + " & Value: " + value;
        }
    }

    public static <T extends KnapSackItem> List<T> knapsack01(T[] knapsackItems, int W) {
        sort(knapsackItems, comparingInt(T::weight));

        int[][] arr = new int[knapsackItems.length][W + 1];

        for (int itemIdx = 0; itemIdx < arr.length; itemIdx++) {
            T item = knapsackItems[itemIdx];

            for (int w = 1; w <= W; w++)
                if (w < item.weight)
                    arr[itemIdx][w] = itemIdx == 0 ? 0 : arr[itemIdx - 1][w];
                else
                    arr[itemIdx][w] = itemIdx == 0 ? item.value : max(item.value + arr[itemIdx - 1][w - item.weight], arr[itemIdx - 1][w]);

        }

        return output(knapsackItems, W, arr);
    }

    private static <T extends KnapSackItem> List<T> output(T[] knapsackItems, int W, int[][] arr) {
        List<T> items = new LinkedList<>();

        for (int w = W, itemIdx = knapsackItems.length - 1; w > 0 && itemIdx > 0; itemIdx--) {
            if (arr[itemIdx][w] == arr[itemIdx - 1][w])
                // so, we could not have taken this item. Need to go upward.
                while (--itemIdx > 0 && arr[itemIdx][w] == arr[itemIdx - 1][w]);

            T item = knapsackItems[itemIdx];

            if (w >= item.weight) {// also handles case if itemIdx = 0
                items.add(item);
                w -= item.weight;
            }
        }

        return items;
    }

}
