package algo.numberTheoreticAlgorithm;

import static java.lang.Math.abs;

public final class Basics {
    private Basics() {}

    public static int[] divisionTheorem(int a, int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n can not be zero or negative");
        int q = a / n, r = a - q * n;

        if (r < 0) {
            r += n;
            q -= 1;
        }
        return new int[] { q, r };
    }

    public static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    public static int binaryGCD(int a, int b) {
        if (b > a)
            return binaryGCD(b, a);

        if (b <= 1)
            return a;

        boolean aEven = (a & 1) == 0, bEven = (b & 1) == 0;

        if (aEven & bEven)
            return 2 * binaryGCD(a >> 1, b >> 1);

        if (aEven)
            return binaryGCD(a >> 1, b);

        if (bEven)
            return binaryGCD(a, b >> 1);

        return binaryGCD(b, abs(a - b) >> 1);

    }

    public static int[] gcdExtended(int a, int b) {
        if (b == 0)
            return new int[] { a, 1, 0 };

        int[] z = gcdExtended(b, a % b);

        int x = z[2], y = z[1] - a / b * z[2];

        z[1] = x;
        z[2] = y;

        return z;
    }

    /**
     * To find x such that:
     *  ax = 1 (modulo n)
     * @param a
     * @param n
     * @return
     */
    public static int multiplicativeInverse(int a, int n) {
        int[] arr = gcdExtended(a, n);

        if (arr[0] != 1)
            throw new IllegalArgumentException("a and n are not coprime");

        return arr[1] < 0 ? arr[1] + n : arr[1];
    }

    public static int orderOfElement(int a, int n) {
        return n / gcd(a, n);
    }

    /**
     * To solve:
     * 			ax = b (modulo n)
     * @param a
     * @param b
     * @param n
     * @return
     */

    public static boolean isSolvable(int a, int b, int n) {
        return b % gcd(a, n) == 0;
    }

    public static int[] modularLinearEquationSolver(int a, int b, int n) {
        int arr[] = gcdExtended(a, n), d = arr[0];
        if (b % d == 0) {
            int x0 = arr[1] * b / d % n;
            int[] out = new int[d];
            for (int i = 0; i < d; i++)
                out[i] = (x0 + i * (n / d)) % n;
            return out;
        }

        throw new IllegalArgumentException("No solution exists");

    }
}
