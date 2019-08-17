package algo.others;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.signum;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MaxPointOnLine {

    public static int maxPoints(int[][] points) {
        if (points.length < 3)
            return points.length;

        int best = 2;

        for (int i = 0; i < points.length - 1; i++)
            best = max(best, bestFromIthPoint(points, i));

        return best;
    }

    private static int bestFromIthPoint(int[][] points, int i) {
        int[] from = points[i];

        Map<String, Integer> samePtSlpoe = new HashMap<>();

        int overLap = 0;

        for (int j = i + 1; j < points.length; j++) {
            int[] to = points[j];

            if (Arrays.equals(from, to)) {
                overLap++;
                continue;
            }

            samePtSlpoe.merge(slope(from, to), 1, Integer::sum);
        }

        return 1 + overLap + getLargest(samePtSlpoe.values());// +1 to include current point.

    }

    private static int getLargest(Collection<Integer> ints) {
        return ints.isEmpty() ? 0 : Collections.max(ints);
    }

    private static String slope(int[] a, int[] b) {
        return slope(a[0] - b[0], a[1] - b[1]);
    }

    private static String slope(int deltaX, int deltaY) {
        int sign = (int) signum(deltaX * deltaY);

        if (sign == 0)
            sign = 1;

        deltaX = abs(deltaX);
        deltaY = abs(deltaY);

        int gcd = gcd(deltaX, deltaY);

        deltaX /= gcd;
        deltaY /= gcd;

        deltaY *= sign;

        return deltaX + "," + deltaY;
    }

    private static int gcd(int a, int b) {

        if (a < b)
            return gcd(b, a);

        if (b == 0)
            return max(a, 1);

        int r = a % b;

        while (r != 0) {
            a = b;
            b = r;
            r = a % b;
        }

        return b;
    }

    public static void main(String[] args) {
        // t[[2,3],[3,3],[-5,3]]
        int[][] input = { { 2, 3 }, { 3, 3 }, { -5, 3 } };
//        int[][] input = { { 1, 1 }, { 3, 2 }, { 5, 3 }, { 4, 1 }, { 2, 3 }, { 1, 4 } };
        System.out.println(maxPoints(input));
    }
}
