package algo.others;

import java.util.Arrays;

public class RemovingDuplicatesFromSortedArr2 {
    public static int removeDuplicates(int[] nums) {
        int i = 0;

        for (int j = i; j < nums.length;) {
            int n = count(nums, j), e = nums[j];
            nums[i++] = e;

            if (n > 1)
                nums[i++] = e;

            j += n;
        }

        return i;
    }

    private static int count(int[] arr, int from) {
        int i = from, a = arr[i++];

        while (i < arr.length)
            if (arr[i] == a)
                i++;
            else
                break;

        return i - from;
    }

    public static void main(String[] args) {
        int[] arr = { 1, 1, 1, 2, 2, 3 };
        System.out.println(Arrays.toString(arr));
        System.out.println(removeDuplicates(arr));
        System.out.println(Arrays.toString(arr));
    }

}
