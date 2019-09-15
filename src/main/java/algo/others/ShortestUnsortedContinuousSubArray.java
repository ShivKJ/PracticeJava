package algo.others;

import static java.util.Objects.isNull;

public class ShortestUnsortedContinuousSubArray {
    public static int findUnsortedSubarray(int[] nums) {
        if (isNull(nums) || nums.length == 0)
            return 0;
        int a = -1, m = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length - 1; i++)
            if (nums[i] > nums[i + 1] && nums[i + 1] < m) {
                a = i;
                m = nums[i + 1];
            }

        System.out.println(a);

        if (a == -1)
            return 0;

        int starting = 0;

        for (int i = 0; i < nums.length; i++)
            if (nums[i] > m) {
                starting = i;
                break;
            }
        System.out.println(starting);
        
        // ------------------------------------------
        a = -1;
        for (int i = nums.length - 1; i > 0; i--)
            if (nums[i] > nums[i - 1]) {
                a = i;
                break;
            }
        int ending = nums.length - 1;

        for (int i = nums.length - 1; i > starting; i--) {
            if (nums[i] > nums[i - 1])
                ;
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] intput = { 2, 6, 4, 8, 10, 9, 15 };
        System.out.println(findUnsortedSubarray(intput));
    }
}
