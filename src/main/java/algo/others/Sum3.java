package algo.others;

import static java.util.Arrays.asList;
import static java.util.Arrays.sort;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

import java.util.LinkedList;
import java.util.List;

public class Sum3 {
    public static List<List<Integer>> threeSum(int[] nums) {
        if (isNull(nums) || nums.length < 3)
            return emptyList();

        sort(nums);

        List<List<Integer>> output = new LinkedList<>();

        for (int cur = 0; cur < nums.length; cur++) {
            if (cur > 0 && nums[cur] == nums[cur - 1])
                continue;

            int left = cur + 1, right = nums.length - 1;

            int e = nums[cur];

            while (left < right) {
                int sum = e + nums[left] + nums[right];

                if (sum == 0) {
                    output.add(asList(e, nums[left], nums[right]));

                    while (left < right && nums[left] == nums[left + 1])
                        left++;

                    while (left < right && nums[right] == nums[right - 1])
                        right--;

                    left++;
                    right--;

                } else if (sum > 0)
                    right--;
                else
                    left++;
            }
        }

        return output;
    }

    public static void main(String[] args) {
//        int[] intput = { -1, 0, 1, 2, -1, -4 };
        int[] intput = { 1, -1, -1, 0 };

        System.out.println(threeSum(intput));
    }
}
