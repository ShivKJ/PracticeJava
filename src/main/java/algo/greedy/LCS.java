package algo.greedy;

import static java.lang.Math.max;

public class LCS {
	public static int[][] lcs(char[] a, char[] b) {
		//TODO: add doc and examples
		final int A = a.length , B = b.length , m[][] = new int[A + 1][B + 1];

		for (int i = 0; i < A; i++)
			for (int j = 0; j < B; j++)
				m[i + 1][j + 1] = a[i] == b[j] ? m[i][j] + 1 : max(m[i + 1][j], m[i][j + 1]);

		return m;
	}

}
