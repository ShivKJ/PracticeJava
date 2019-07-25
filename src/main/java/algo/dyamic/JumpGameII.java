package algo.dyamic;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Arrays.fill;

public class JumpGameII {

    public static int jumpDpFromRightToLeft(int[] jumps) {
        int n = jumps.length;

        int[] out = new int[n];

        fill(out, 0, n - 1, n);

        for (int i = n - 2; i >= 0; i--)
            for (int k = min(n - 1, i + jumps[i]); k > i; k--)
                out[i] = min(out[i], 1 + out[k]);

        return out[0];
    }

    public static int jumpDpFromLeftToRight(int[] jumps) {
        int n = jumps.length;

        int[] out = new int[n];

        fill(out, 1, n, n);

        for (int i = 1; i < n; i++)
            for (int k = 0; k < i; k++)
                if (jumps[k] + k >= i) {
                    out[i] = min(out[i], 1 + out[k]);
                    break;
                }

        return out[n - 1];

    }

    public static int jump(int[] arr) {
        int steps = 0;

        for (int box = 0, n = arr.length, best = 0, bestAllTime = 0; box < n - 1; box++) {
            bestAllTime = max(bestAllTime, box + arr[box]);

            if (bestAllTime >= n - 1)
                return steps + 1;
            else if (box == best) {
                if (box == bestAllTime)
                    throw new IllegalArgumentException("Can not reach to end location");

                best = bestAllTime;
                steps++;
            }

        }

        return steps;
    }

    public static void main(String[] args) {
        System.out.println(jump(new int[] { 2, 3, 1, 1, 4 }));
        System.out.println(jump(new int[] { 1, 2, 0, 1, 3 }));
        System.out.println(jump(new int[] { 1, 2, 0, 1, 3 }));
        System.out.println(jump(new int[] { 1, 2, 0, 1, 1, 3 }));

    }
}
