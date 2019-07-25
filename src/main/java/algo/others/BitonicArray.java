package algo.others;

import static java.lang.Math.max;
import static java.util.Arrays.binarySearch;

public final class BitonicArray {
    private BitonicArray() {}

    public static int search(int[] arr, int k) {
        return search(arr, k, 0, arr.length - 1);
    }

    public static int search(int[] arr, int k, int from, int to) {

        if (from == to)// base case
            return arr[from] == k ? from : -1;

        int mid = (from + to) >> 1;
        int e = arr[mid];

        if (k < e) {
            int index = bs(arr, k, from, mid - 1);
            return index > -1 ? index : rbs(arr, k, mid + 1, to);
        } else if (k > e)
            return arr[mid - 1] < arr[mid] ? search(arr, k, mid + 1, to) : search(arr, k, from, mid - 1);
        else
            return mid;

    }

    public static int bs(int[] arr, int k, int lo, int high) {
        return max(binarySearch(arr, lo, high + 1, k), -1);
    }

    public static int rbs(int[] arr, int k, int lo, int high) {

        while (lo <= high) {
            int mid = (lo + high) >> 1;

            int e = arr[mid];

            if (e < k)
                high = mid - 1;
            else if (e > k)
                lo = mid + 1;
            else
                return mid;
        }

        return -1;
    }

}
