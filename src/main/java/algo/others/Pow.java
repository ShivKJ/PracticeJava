package algo.others;

import static java.lang.Integer.MIN_VALUE;

public class Pow {
    public static double myPow(double x, int n) {
        if (n == 0)
            return 1;
        
        double y = myPow(x, n / 2);

        return y * y * (n % 2 == 0 ? 1 : (n < 0 ? 1 / x : x));
    }

    public static double myPowIteration(double x, int n) {
        if (n == MIN_VALUE)
            return myPowIteration(x * x, n / 2);// as MIN_VALUE is even.

        if (n < 0)
            return myPowIteration(1 / x, -n);

        double out = 1;

        while (n > 0) {
            if (n % 2 == 1)
                out *= x;

            x *= x;
            n >>= 1;
        }

        return out;
    }

    public static void main(String[] args) {
        System.out.println(myPow(2, -3));

        System.out.println(myPowIteration(2, 5));

    }
}
