package algo.others;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;
import static java.lang.System.arraycopy;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.sort;

import java.util.Arrays;
import java.util.Random;

public final class Merging {

    public static int[] mergingSortedArray(int[] arr1, int[] arr2) {
        int[] out = new int[arr1.length + arr2.length];

        for (int i = 0, j = 0, idx = 0; idx < out.length;) {
            out[idx++] = arr1[i] < arr2[j] ? arr1[i++] : arr2[j++];

            if (i == arr1.length) {
                arraycopy(arr2, j, out, idx, arr2.length - j);
                idx += arr2.length - j;
            }

            if (j == arr2.length) {
                arraycopy(arr1, i, out, idx, arr1.length - i);
                idx += arr1.length - i;
            }
        }

        return out;
    }

    public static int[] mergingSortedArrayDirty(int[] arr, int p, int q, int r) {
        int[] tmp = copyOfRange(arr, p, r + 1);

        for (int idx = p, i1 = 0, i2 = q + 1 - p; idx <= r; idx++) {
            int index;

            if (i1 > q - p)
                index = i2++;
            else if (i2 > r - p)
                index = i1++;
            else if (tmp[i1] <= tmp[i2])
                index = i1++;
            else
                index = i2++;

            arr[idx] = tmp[index];
        }

        return arr;
    }

    public static int[] mergingSortedArrayClean(int[] arr, int p, int q, int r) {
        int[] tmp = new int[r - p + 3];

        arraycopy(arr, p, tmp, 0, q - p + 1);
        arraycopy(arr, q + 1, tmp, q - p + 2, r - q);

        tmp[q - p + 1] = tmp[r - p + 2] = MAX_VALUE;

        for (int idx = p, i1 = 0, i2 = q - p + 2; idx <= r; idx++)
            arr[idx] = tmp[i1] < tmp[i2] ? tmp[i1++] : tmp[i2++];

        return arr;
    }

    public static void mergeSort(int[] arr, int p, int q) {
        if (p < q) {
            int mid = (p + q) >> 1;

            mergeSort(arr, p, mid);
            mergeSort(arr, mid + 1, q);

            if (arr[mid] > arr[mid + 1])
                mergingSortedArrayClean(arr, p, mid, q);
        }
    }

    public static void mergeSortBottomUp(int[] arr) {
        int n = arr.length;

        for (int w = 1; w < n; w *= 2)
            for (int p = 0; p < n - w; p += 2 * w) {
                int mid = p + w - 1;
                if (arr[mid] > arr[mid + 1])
                    mergingSortedArrayClean(arr, p, mid, min(mid + w, n - 1));
            }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergingSortedArray(new int[] { -1, 2, 10, 15 }, new int[] { -1, 4, 5, 10, 20 })));
        int[] c = { -1, 2, 10, 15, -1, 4, 5, 10, 20 };
        System.out.println(Arrays.toString(mergingSortedArrayDirty(c, 0, 3, c.length - 1)));
        c = new int[] { -1, 2, 10, 15, -1, 4, 5, 10, 20 };
        mergingSortedArrayClean(c, 0, 3, c.length - 1);

        c = new Random().ints(100000, 0, 1000).toArray();
        int[] sorted = c.clone();
        sort(sorted);

        // mergeSort(c, 0, c.length - 1);
        mergeSortBottomUp(c);
        System.out.println(Arrays.equals(sorted, c));

    }
}
