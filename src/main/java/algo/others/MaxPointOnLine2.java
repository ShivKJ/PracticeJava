package algo.others;

import static java.lang.Math.max;

import java.util.function.Predicate;

public class MaxPointOnLine2 {
    public static int maxPoints(int[][] points) {
        if (points.length < 3)
            return points.length;

        int best = 2;

        for (int i = 0; i < points.length - 1; i++) {
            int[] a = points[i], b = points[i + 1];

            long[] slope = { a[0] - b[0], a[1] - b[1] };

            boolean isZeroSlope = slope[0] == 0 && slope[1] == 0;
            Predicate<int[]> onLine = isZeroSlope ? v -> samePoint(a, v) : v -> onLine(a, v, slope);

            int pts = 0;

            for (int j = 0; j < points.length; j++)
                if (onLine.test(points[j]))
                    pts++;

            best = max(best, pts);
        }

        return best;
    }

    private static boolean onLine(int[] a, int[] b, long[] slope) {
        return (a[0] - b[0]) * slope[1] == (a[1] - b[1]) * slope[0];
    }

    private static boolean samePoint(int[] a, int[] b) {
        return a[0] == b[0] && a[1] == b[1];
    }

    public static void main(String[] args) {
//        int[][] input = { { 84, 250 }, { 0, 0 }, { 1, 0 }, { 0, -70 }, { 0, -70 }, { 1, -1 }, { 21, 10 }, { 42, 90 }, { -42, -230 } };
        int[][] input = { { 1, 1 }, { 1, 1 }, { 1, 1 } };
        System.out.println(maxPoints(input));

    }

    public static int getHash(int[] p) {
        return System.identityHashCode(p);
    }

}
