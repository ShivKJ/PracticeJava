package algo.dyamic;

public final class FibonacciSequence {

    public static int fib(int n) {
        if (n < 2)
            return n;

        int[] f = new int[n];
        f[1] = 1;

        for (int i = 2; i < n; i++)
            f[i] = f[i - 1] + f[i - 2];

        return f[n - 1];
    }

    public static int fibOrderNSpace(int n) {
        if (n < 2)
            return n;

        int a = 0, b = 1;

        for (int i = 2; i < n; i++) {
            b += a;
            a = b - a;
        }

        return b;
    }

}
