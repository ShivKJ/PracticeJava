package algo.practice;

import java.util.Collection;

public class StringPermutation {

	/**
	 * Generates all permutation of a String containing distinct characters.
	 * for example: 
	 * permutation of "abc": abc, acb, bac, bca, cab, cba
	 * 
	 * To use function: Create a collection to hold permutation, envoke
	 * perm("",str,collectionToHoldPerm)
	 * 
	 * @param prefix
	 * @param str
	 * @param permutations
	 */
	public static void permu(String prefix, String str, Collection<String> permutations) {
		if (str.isEmpty())
			permutations.add(prefix);
		else {
			for (int i = 0; i < str.length(); i++)
				permu(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1), permutations);

		}
	}

}
