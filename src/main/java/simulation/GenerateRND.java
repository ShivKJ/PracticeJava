package simulation;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.copyOf;
import static java.util.Arrays.parallelPrefix;

import java.util.Arrays;

public class GenerateRND {

	private GenerateRND() {}

	public static int ints(int[] x, double[] p) {
		/**
		 * need to add documentation and checks on values of p, and that sum of pi is 1.
		 */
		p = copyOf(p, p.length);
		double y = Math.random();
		parallelPrefix(p, Double::sum);
		int pos = binarySearch(p, y);

		if (pos < 0)
			pos = -pos - 1;

		return x[pos];
	}

	public static void main(String[] args) {
		int[] p = { 1, 2, 3, 4 };
		parallelPrefix(p, Integer::sum);
		System.out.println(Arrays.toString(p));
		System.out.println(binarySearch(p, -1));
	}
}
