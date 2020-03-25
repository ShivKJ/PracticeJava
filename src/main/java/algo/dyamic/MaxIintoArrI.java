package algo.dyamic;

import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

public class MaxIintoArrI {
    /**
     * maximizing sum of i * N[i] when removing some element of N
     * @param N
     * @param visualize
     * @return
     */
    private static int maxSum(int[] N, boolean visualize) {
        int n = N.length;

        int[][] dp = new int[n][n];

        int out = MIN_VALUE;

        for (int r = 0; r < n; r++)
            for (int c = r; c < n; c++) {
                int rthElementWeight = (r + 1) * N[c]; // i * N[i] where i is index 1,2,3...,length of array

                if (r == 0)
                    if (c == 0)
                        dp[r][c] = rthElementWeight;// entry = N[0]
                    else
                        dp[r][c] = max(dp[r][c - 1], rthElementWeight); // max entry in 0th row so far
                else
                    dp[r][c] = dp[r - 1][c - 1] + rthElementWeight;// element just diagonally above + new sum

                out = max(dp[r][c], out);
            }

        if (visualize)
            visualize(dp);

        return out;
    }

    private static void visualize(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (j < i)
                    System.out.format("%-5s", "*");
                else
                    System.out.format("%- 5d", arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println(maxSum(new int[] { -1, -9, 0, 5, -7 }, false));
        System.out.println(maxSum(new int[] { -1, 3, 4 }, false));
    }

}
