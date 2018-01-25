package algo.textprocessing;

public class SubStringSearcher {
	public static boolean simpleSubStringMatch(String big, String small) {
		char[] a = big.toCharArray() , b = small.toCharArray();

		for (int i = 0; i <= a.length - b.length; i++)
			if (isSubString(a, b, i))
				return true;

		return false;
	}

	private static boolean isSubString(char[] a, char[] b, int from) {
		for (int i = 0; i < b.length; i++)
			if (a[from + i] != b[i])
				return false;

		return true;
	}

	public static int[] failureFunction(char[] pattern) {
		int L = pattern.length , fail[] = new int[L] , shift = 0 , cur = 1;

		while (cur < L)
			if (pattern[cur] == pattern[shift])
				fail[cur++] = ++shift;
			else if (shift > 0)
				shift = fail[shift - 1];
			else
				cur++;

		return fail;
	}
}
