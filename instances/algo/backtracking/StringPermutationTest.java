package algo.backtracking;

import static java.lang.System.out;

public class StringPermutationTest {

	public static void main(String[] args) {
		if (args.length == 0)
			args = new String[] { "SKJ" };

		StringPermutation.permutation(args[0]).forEach(out::println);
	}
}
