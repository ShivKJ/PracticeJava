package algo.others;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.copyOf;

import java.util.Arrays;

public class MergeSortedArray {
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums2.length == 0)
            return;

        int left = 0, right = 0;

        int[] copyLeft = copyOf(nums1, m + 1);
        copyLeft[m] = MAX_VALUE;

        for (int i = 0; i < m + n; i++) {
            if (right < n && copyLeft[left] > nums2[right]) {
                nums1[i] = nums2[right++];
            } else
                nums1[i] = copyLeft[left++];
        }
    }

    public static void main(String[] args) {
        int[] a = { 2, 0 };
        int[] b = { 1 };
        merge(a, 1, b, b.length);
//        int[] a = { 1, 2, 3, 0, 0, 0 };
//        int[] b = { 2, 5, 6 };
//        merge(a, 3, b, 3);

        System.out.println(Arrays.toString(a));
    }
}
