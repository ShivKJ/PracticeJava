package numerical.coefficient;

import static java.lang.Math.E;
import static java.lang.Math.log;
import static java.lang.Math.pow;

public class Coefficient {

	public static double binomialDistributionCoeff(int n, int r, double p) {

		if (r > n - r)
			return binomialDistributionCoeff(n, n - r, 1 - p);

		double z = 0;

		for (int i = 0; i < r; i++)
			z += log(n - i) - log(i + 1);

		z += r * log(p) + (n - r) * log(1 - p);

		return pow(E, z);
	}
}
