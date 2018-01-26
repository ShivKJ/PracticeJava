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
	
	//---------------------------------------------------------------------------

	public static int kmp(char[] text, char[] pattern) {
		final int l = pattern.length;

		if (l == 0)
			return 0;

		final int L = text.length , fail[] = failureFunction(pattern);
		int pattIndex = 0 , textIndex = 0;

		while (textIndex < L)
			if (text[textIndex] == pattern[pattIndex]) {
				if (pattIndex == l - 1)
					return textIndex - pattIndex;

				textIndex++;
				pattIndex++;
				
			} else if (pattIndex > 0)
				pattIndex = fail[pattIndex - 1];
			else
				textIndex++;

		return -1;

	}

	/**
	 * This function is used in KMP to avoid unnecessary comparison of pattern with text.
	 * If text matches up to some length with pattern, and then there is mismatch
	 * then with help of the failure function, we can start afresh comparison from 
	 * the very mismatch point in text, no need to go back to any previous char in 
	 * text for comparison.
	 * 
	 * prefixLength[index]: length of longest prefix which is also a suffix for this index.
	 * 
	 * @param pattern
	 * @return prefixLength array
	 */
	public static int[] failureFunction(char[] pattern) {
		final int L = pattern.length , prefixLength[] = new int[L];
		int prefixIndex = 0 , idx = 1;

		/*
		 * Example:
		 *   index            0  1  2  3  4  5  6  7  8  9  10 
		 *   pattern          a  b  a  x  y  _  a  b  a  a  b
		 *   prefixLength     0  0  1  0  0  0  1  2  3  1  2
		 */

		while (idx < L)
			if (pattern[idx] == pattern[prefixIndex])
				/*
				 *       prefixIndex = 2 and index = 8
				 *       
				 *       a  b  a  x  y  _  a  b  a  a  b
				 *             ^                 ^
				 *             |                 |
				 *         prefixIndex         index
				 * 
				 *   Now prefixIndex -> prefixIndex + 1
				 *   
				 *   So, for index 8, suffix which is also a prefix has length 3.
				 *   
				 *   Setting index -> index + 1 for next comparison. 
				 */
				prefixLength[idx++] = ++prefixIndex;
			else if (prefixIndex > 0)
				/*
				 *       prefixIndex = 3 and index = 9
				 *       
				 *       a  b  a  x  y  _  a  b  a  a  b
				 *                ^                 ^
				 *                |                 |
				 *            prefixIndex         index
				 *       
				 *   Starting over from prefixIndex of "prefixIndex" of previous char. 
				 *   
				 *       prefixIndex = 1 and index = 9
				 *       
				 *       a  b  a  x  y  _  a  b  a  a  b
				 *          ^                       ^
				 *          |                       |
				 *      prefixIndex               index
				 */
				prefixIndex = prefixLength[prefixIndex - 1];
			else
				/*
				 *       prefixIndex = 0 and index = 11
				 *       
				 *       a  b  a  x  y  _  a  b  a  a  b
				 *       ^        ^
				 *       |        |
				 *    prefixIndex index
				 *    
				 *    Setting index -> index + 1 for next comparison.
				 */
				idx++;

		return prefixLength;
	}

}
