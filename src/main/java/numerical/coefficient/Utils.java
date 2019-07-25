package numerical.coefficient;

import static java.lang.Math.E;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;

import java.math.BigInteger;

public class Utils {

    public static double binomialDistributionCoeff(int n, int r, double p) {
        if (r > n - r)
            return binomialDistributionCoeff(n, n - r, 1 - p);

        double z = 1;

        for (int i = r; i > 0; i--)
            z *= p / (1 - p) * (n - i + 1) / i;

        return pow(E, log(z) + n * log(1 - p));
    }

    public static BigInteger binomialCoefficient(int n, int r) {
        BigInteger z = ONE;

        for (int i = 0; i < r; i++)
            z = z.multiply(valueOf(n - i)).divide(valueOf(i + 1));

        return z;
    }

}
