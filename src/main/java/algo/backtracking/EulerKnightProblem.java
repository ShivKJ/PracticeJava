package algo.backtracking;

import java.util.LinkedList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class EulerKnightProblem {

	/**
	 *
	 *
	 */
	private final int[][] movement;
	private final int totalSteps;
	private final int N;

	private EulerKnightProblem(int n) {
		this.N = n;
		this.totalSteps = n * n;
		this.movement = new int[n][n];

	}

	public static int[][] solve(int n) {
		return solve(0, 0, n);
	}

	public static int[][] solve(int startRow, int startCol, int n) {
		if (startRow >= n || startCol >= n)
			throw new IllegalArgumentException(startRow + " , " + startCol + " is not a valid start for N: " + n);

		EulerKnightProblem problem = new EulerKnightProblem(n);
		problem.movement[startRow][startCol] = 1;

		if (problem.isCorrect(startRow, startCol, 1))
			return problem.movement;

		return new int[0][0];
	}

	private boolean isCorrect(int r, int c, int step) {
		if (step == totalSteps)
			return true;

		for (int[] move : nextMoves(r, c)) {
			int nextR = move[0] , nextC = move[1];
			movement[nextR][nextC] = step + 1;

			if (!isCorrect(nextR, nextC, step + 1))
				movement[nextR][nextC] = 0;
			else
				return true;

		}
		return false;
	}

	private List<int[]> nextMoves(int r, int c) {
		List<int[]> moves = new LinkedList<>();

		if (r + 2 < N) {
			if (c + 1 < N && movement[r + 2][c + 1] == 0)
				moves.add(new int[] { r + 2, c + 1 });
			if (c - 1 >= 0 && movement[r + 2][c - 1] == 0)
				moves.add(new int[] { r + 2, c - 1 });
		}

		if (r - 2 >= 0) {
			if (c + 1 < N && movement[r - 2][c + 1] == 0)
				moves.add(new int[] { r - 2, c + 1 });

			if (c - 1 >= 0 && movement[r - 2][c - 1] == 0)
				moves.add(new int[] { r - 2, c - 1 });
		}

		if (c + 2 < N) {
			if (r + 1 < N && movement[r + 1][c + 2] == 0)
				moves.add(new int[] { r + 1, c + 2 });

			if (r - 1 >= 0 && movement[r - 1][c + 2] == 0)
				moves.add(new int[] { r - 1, c + 2 });
		}
		if (c - 2 >= 0) {
			if (r + 1 < N && movement[r + 1][c - 2] == 0)
				moves.add(new int[] { r + 1, c - 2 });

			if (r - 1 >= 0 && movement[r - 1][c - 2] == 0)
				moves.add(new int[] { r - 1, c - 2 });
		}

		return moves;
	}

	public static void print(int[][] steps) {
		stream(steps).map(x -> stream(x).mapToObj(y -> format("%-5d", y)).collect(joining())).forEach(out::println);
	}

}
