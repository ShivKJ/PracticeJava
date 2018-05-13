package algo.backtracking;

import static java.lang.Math.abs;

public class NQueenProblem {
	public static int[] solution(int n) {
		int[] position = new int[n];
		return solution(position, 0) ? position : new int[0];
	}

	public static boolean solution(int[] pos, int row) {
		if (pos.length == row)
			return true;

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
				System.out.print((k == i ? 'Q' : '*') + "  ");
			System.out.println();
		}
	}

	public static void main(String[] args) {
		print(solution(4));
	}

}
