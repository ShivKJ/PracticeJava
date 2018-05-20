package algo.backtracking;

import static java.lang.Integer.parseInt;

public class NQueenProblemTest {
	public static void main(String[] args) {
		if (args.length == 0)
			args = new String[] { "5" };

		NQueenProblem.solution(parseInt(args[0]), true).forEach(NQueenProblem::print);
	}
}
