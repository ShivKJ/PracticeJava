package algo.dyamic;

import static java.lang.Math.max;

public class MaxSubArraySum {
    public static int sumDP(int[] arr) {
        int[] dp = new int[arr.length];
        dp[0] = arr[0];

        for (int i = 1; i < arr.length; i++) {
            dp[i] = max(arr[i], dp[i - 1] + arr[i]);
        }

        return best(dp);
    }

    private static int best(int[] arr) {
        int out = Integer.MIN_VALUE;
        for (int a : arr)
            if (out < a)
                out = a;

        return out;
    }

    public static int sumDPNoSpace(int[] arr) {
        int globalBest = arr[0], sum = globalBest;
        int start = 0, end = 0, possibleStart = 0;

        for (int i = 1; i < arr.length; i++) {
            if (sum + arr[i] < 0)
                possibleStart = i + 1;

            sum = max(sum + arr[i], arr[i]);

            if (sum > globalBest) {
                globalBest = sum;
                start = possibleStart;
                end = i;
            }
        }

        System.out.println("Start:" + start + "  End:" + end);

        return globalBest;
    }

    public static void main(String[] args) {
//        System.out.println(sumDP(new int[] { 1, 2, 3, -3 }));
//        System.out.println(sumDPNoSpace(new int[] { -1, 1, 2, 3, -6, 10 }));
        System.out.println(sumDPNoSpace(new int[] { -10, -10, 12 }));

    }
}
