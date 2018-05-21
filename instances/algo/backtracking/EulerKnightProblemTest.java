package algo.backtracking;

import static java.lang.Integer.parseInt;

public class EulerKnightProblemTest {
	public static void main(String[] args) {
		if (args.length == 0)
			args = new String[] { "5" };

		EulerKnightProblem.print(EulerKnightProblem.solve(parseInt(args[0])));
	}
}
