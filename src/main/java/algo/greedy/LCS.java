package algo.greedy;

import static java.lang.Math.max;

public class LCS {
	/**
	 * Finds longest common sub-sequence in given two String.
	 * Methods uses Dynamic programming approach, to utilize
	 * already calculated results for sub-problem.
	 * 
	 * Let X = <x[1], x[2], ... x[m]> and Y = <y[1],  y[2], ... y[n]> 
	 * be sequences, and let Z = <z[1], ... z[k]> be any LCS of X and Y .
	 * 
	 * 1. If x[m] = y[n], then z[k] = x[m] = y[n] and Z[1:k-1] is an 
	 *    LCS of X[1:m-1] and Y[n-1].
	 * 2. If x[m] is not y[n] , then  ́z[k] is not x[m] implies that Z 
	 * 	  is an LCS of X[1:m-1] and Y .
	 * 3. If x[m] is not y[n] , then  ́z[k] is not y[n] implies that Z 
	 * 	  is an LCS of X and Y[1:n-1].
	 * 
	 * @param a
	 * @param b
	 * 
	 * @return memoization_matrix
	 */
	public static String lcs(char[] a, char[] b) {

		final int A = a.length , B = b.length , m[][] = new int[A + 1][B + 1];

		int i = 0 , j = 0;

		for (; i < A; i++, j = 0)
			for (; j < B; j++)
				m[i + 1][j + 1] = a[i] == b[j] ? m[i][j] + 1 : max(m[i + 1][j], m[i][j + 1]);

		final int L = m[A][B];

		i = A;
		j = B;

		if (L > 0) {
			final char[] result = new char[L];
			int k = L - 1;

			do {
				final int f = m[i][j];

				while (f == m[i - 1][j])
					i--;
				while (f == m[i][j - 1])
					j--;

				result[k--] = a[i - 1];
			} while (--i > 0 && --j > 0);

			return new String(result);
		}

		return "";
	}

}
