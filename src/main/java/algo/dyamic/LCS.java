package algo.dyamic;

import static java.lang.Math.max;
import static java.util.Arrays.fill;
import static java.util.Collections.singletonList;

import java.util.LinkedList;
import java.util.List;

public class LCS {
	private final static String EMPTY_STRING = "";

	/**
	 * Finds longest common sub-sequence in given two String.
	 * <br>
	 * Methods uses Dynamic programming approach, to utilize
	 * already calculated results for sub-problem.
	 * <br>
	 * Let X = {x[1], x[2], ... x[m]} and Y = {y[1],  y[2], ... y[n]} 
	 * be sequences, and let Z = {z[1], ... z[k]} be any LCS of X and Y .
	 * <br>
	 * 1. If x[m] = y[n], then z[k] = x[m] = y[n] and Z[1:k-1] is an 
	 *    LCS of X[1:m-1] and Y[n-1].<br>
	 * 2. If x[m] is not y[n] , then  ́z[k] is not x[m] implies that Z 
	 * 	  is an LCS of X[1:m-1] and Y .<br>
	 * 3. If x[m] is not y[n] , then  ́z[k] is not y[n] implies that Z 
	 * 	  is an LCS of X and Y[1:n-1].
	 * <br>
	 * @param a
	 * @param b
	 * 
	 * @return Largest Common String
	 */
	public static String lcs(char[] a, char[] b) {

		final int A = a.length , B = b.length , MAT[][] = new int[A + 1][B + 1];

		int i = 0 , j = 0;

		for (; i < A; i++, j = 0)
			for (; j < B; j++)
				MAT[i + 1][j + 1] = a[i] == b[j] ? MAT[i][j] + 1 : max(MAT[i + 1][j], MAT[i][j + 1]);

		final int LCS_LENGTH = MAT[A][B];

		if (LCS_LENGTH > 0) {
			final char[] result = new char[LCS_LENGTH];
			int k = LCS_LENGTH - 1;

			i = A;
			j = B;

			do {
				final int f = MAT[i][j];

				while (f == MAT[i - 1][j])
					i--;
				while (f == MAT[i][j - 1])
					j--;

				result[k--] = a[i - 1];
			} while (--i > 0 && --j > 0);

			return new String(result);
		}

		return EMPTY_STRING;
	}

	/**
	 * Finds largest monotonically increasing sub sequence in a given array.
	 * @param arr
	 * @return longest monotonically increasing sub-sequence  
	 */
	public static List<Integer> lcs(int[] arr) {
		final int L = arr.length , memoizedArray[] = new int[L];
		fill(memoizedArray, 1);

		@SuppressWarnings("unchecked")
		final List<Integer>[] LCS = new List[L];// LCS[i] = lcs up to arr[0:i]

		List<Integer> output = null;

		for (int i = 0; i < L; i++) {
			for (int j = 0; j < i; j++)
				if (arr[j] < arr[i])
					if (memoizedArray[j] + 1 > memoizedArray[i]) {
						memoizedArray[i] = memoizedArray[j] + 1;
						LCS[i] = LCS[j];
					}

			if (LCS[i] == null)
				LCS[i] = singletonList(arr[i]);
			else {
				LCS[i] = new LinkedList<>(LCS[i]);
				LCS[i].add(arr[i]);
			}

			if (output == null || output.size() < LCS[i].size())
				output = LCS[i];
		}

		return output;
	}
}
