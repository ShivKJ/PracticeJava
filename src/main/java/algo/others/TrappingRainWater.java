package algo.others;

import static java.lang.Math.min;
import static java.lang.System.out;

public class TrappingRainWater {
    /**
     * with of bar is considered zero.
     * 
     * @param height
     * @return
     */
    public static int trap(int[] height) {

        int totalArea = 0, backup = 0, n = height.length, lastLargest = 0;

        for (int i = 0, j = i + 1; j < n; j++) {
            for (; j < n; j++)
                if (height[j] > height[j - 1])
                    break;

            if (j == n)
                j--;

            if (height[i] < height[j]) {
                totalArea += (j - i) * height[i];
                backup = 0;
                i = j;
            } else {
                backup += (j - i) * min(height[lastLargest], height[j]);
                lastLargest = j;
            }

            out.println("A=" + totalArea + " : B=" + backup + " : i=" + i + " : j=" + j + " : largest:" + lastLargest);
        }

        return totalArea + backup;
    }

    public static void main(String[] args) {
//        System.out.println(trap(new int[] { 1, 2, 3, 2, 4 }));
        System.out.println(trap(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
    }
}
