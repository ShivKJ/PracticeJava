package algo.others;

import static java.lang.Math.min;

public class ContainerWithMostWater {
    public static int maxArea(int[] arr) {
        int maxArea = 0;

        for (int l = 0, r = arr.length - 1; l < r;) {
            int area = area(l, r, arr);

            if (area > maxArea)
                maxArea = area;

            if (arr[l] < arr[r])
                l++;
            else
                r--;
        }

        return maxArea;
    }

    private static int area(int i, int j, int[] arr) {
        return min(arr[i], arr[j]) * (j - i);
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 }));
    }
}
