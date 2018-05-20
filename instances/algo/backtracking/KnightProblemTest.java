package algo.backtracking;

import static java.lang.Integer.parseInt;

public class KnightProblemTest {
	public static void main(String[] args) {
		if (args.length == 0)
			args = new String[] { "5" };

		KnightProblem.print(KnightProblem.solve(parseInt(args[0])));
	}
}
