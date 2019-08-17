package algo.others;

import static java.lang.Math.abs;

import java.util.Arrays;

public class SquareOfSortedArray {

    public static int[] sortedSquares(int[] a) {
        int[] out = new int[a.length];

        int i = a.length - 1, u = 0, v = i;

        while (i > -1)
            out[i--] = abs(a[u]) < abs(a[v]) ? a[v] * a[v--] : a[u] * a[u++];

        return out;
    }

    public static void main(String[] args) {
        int[] a = { -7, -3, 2, 3, 11 };
//        int[] a = { -1, 1 };
        System.out.println(Arrays.toString(sortedSquares(a)));
    }
}
