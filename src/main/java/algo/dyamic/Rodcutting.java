package algo.dyamic;

import static java.lang.Math.max;
import static java.lang.System.out;
import static java.util.Arrays.fill;

public final class Rodcutting {
    private static final int UNASSIGNED = -1;

    public static int rodcuttingBottomUp(int[] p) {
        int n = p.length;

        int[] r = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++)
                r[i] = max(r[i - j - 1], p[j]);

            r[i] = max(r[i], p[i]);
        }

        return r[n - 1];
    }

    public static int rodcuttingMemoized(int[] p) {
        int[] r = new int[p.length];

        fill(r, UNASSIGNED);

        return rodcuttingMemoized(p, p.length - 1, r);
    }

    private static int rodcuttingMemoized(int[] p, int n, int[] r) {
        if (r[n] != UNASSIGNED)
            return r[n];

        for (int i = 0; i < n; i++)
            r[n] = max(p[i], rodcuttingMemoized(p, n - i - 1, r));

        r[n] = max(r[n], p[n]);

        return r[n];
    }

    public static void main(String[] args) {
        out.println(rodcuttingBottomUp(new int[] { 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 }));
        out.println(rodcuttingMemoized(new int[] { 1, 5, 8, 9, 10, 17, 17, 20, 24, 30 }));
    }
}
