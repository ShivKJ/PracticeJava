package algo.backtracking;

import static java.lang.Integer.parseInt;

public class AllPathMatrixTest {
	public static void main(String[] args) {

		if (args.length == 0)
			args = new String[] { "2", "3" };

		int rows = parseInt(args[0]) , cols = parseInt(args[1]);

		AllPathInMatrix.paths(rows, cols).forEach(x -> AllPathInMatrix.print(cols, x));
	}
}
