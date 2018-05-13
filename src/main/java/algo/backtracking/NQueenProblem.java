package algo.backtracking;

import static java.lang.Math.abs;
import static java.lang.System.out;
import static java.util.Collections.unmodifiableList;

import java.util.LinkedList;
import java.util.List;

public class NQueenProblem {
	private List<int[]> solutions;

	public List<int[]> solution(int n) {
		this.solutions = new LinkedList<>();

		solution(new int[n], 0);

		return unmodifiableList(solutions);
	}

	private boolean solution(int[] pos, int row) {
		if (pos.length == row) {
			solutions.add(pos.clone());
			return false;// throwing false only to find next solution.
		}

		for (int col = 0; col < pos.length; col++) {
			boolean safe = true;

			for (int r = 0; r < row; r++)
				if (pos[r] == col || abs(col - pos[r]) == abs(r - row)) {
					safe = false;
					break;
				}

			if (safe) {
				pos[row] = col;
				if (solution(pos, row + 1))
					return true;
			}
			
		}

		return false;
	}

	public static void print(int[] pos) {
		for (int i : pos) {
			for (int k = 0; k < pos.length; k++)
				out.print((k == i ? 'Q' : '*') + "  ");
			out.println();
		}
		out.println();
	}

}
