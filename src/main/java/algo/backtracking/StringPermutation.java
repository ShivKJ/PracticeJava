package algo.backtracking;

import java.util.Collection;
import java.util.LinkedList;

public class StringPermutation {
	private StringPermutation() {}

	/**
	 * Generates all permutation of a String containing distinct characters.
	 * for example: 
	 * permutation of "abc": abc, acb, bac, bca, cab, cba
	 * 
	 * To use function: Create a collection to hold permutation, invoke
	 * perm("",str,collectionToHoldPerm)
	 * 
	 * @param prefix
	 * @param str
	 * @param permutations
	 */

	private static void permutation(String prefix, String str, Collection<String> permutations) {
		if (str.isEmpty())
			permutations.add(prefix);
		else {
			for (int i = 0; i < str.length(); i++)
				permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1), permutations);

		}
	}

	public static Collection<String> permutation(String str) {
		Collection<String> per = new LinkedList<>();

		permutation("", str, per);

		return per;
	}

}
