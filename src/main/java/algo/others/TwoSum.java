package algo.others;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.sort;

import java.util.Arrays;

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        int n = nums.length;

        int[] copy = nums.clone();

        sort(copy);

        for (int i = 0; i < n; i++) {
            int idx = binarySearch(copy, i + 1, n, target - copy[i]);

            if (idx > 0)
                return new int[] { indexOfFromLeft(nums, copy[i]), indexOfFromRight(nums, copy[idx]) };
        }

        return null;
    }

    private static int indexOfFromLeft(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++)
            if (nums[i] == k)
                return i;

        return -1;
    }

    private static int indexOfFromRight(int[] nums, int k) {
        for (int i = nums.length - 1; i >= 0; i--)
            if (nums[i] == k)
                return i;

        return -1;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[] { 2, 7, 11, 15 }, 9)));
    }
}
