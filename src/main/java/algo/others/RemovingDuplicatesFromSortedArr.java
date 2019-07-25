package algo.others;

import java.util.Arrays;

public class RemovingDuplicatesFromSortedArr {
    public static int removeDuplicates(int[] nums) {
        int i = 0;

        for (int j = i + 1; j < nums.length; j++) {
            if (nums[j] != nums[i])
                swap(nums, ++i, j);

        }

        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        if (i < j) {
            arr[i] = arr[j] - arr[i];

            arr[j] = arr[j] - arr[i];
            arr[i] += arr[j];
        }
    }

    public static void main(String[] args) {
        int[] arr = { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };
        System.out.println(removeDuplicates(arr));
        System.out.println(Arrays.toString(arr));
    }
}
