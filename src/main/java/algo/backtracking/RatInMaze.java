package algo.backtracking;

import static java.lang.String.format;
import static java.lang.System.out;

import java.util.LinkedList;
import java.util.List;

public class RatInMaze {
	private static final Boolean FREE = true , OCCUPIED = false , BLOCKED = null;

	private final Boolean[][]	mat;
	private final int			endRow , endCol;

	private RatInMaze(boolean[][] blockerMatrix, int startRow, int startCol, int endRow, int endCol) {
		this.mat = new Boolean[blockerMatrix.length][blockerMatrix[0].length];

		for (int i = 0; i < blockerMatrix.length; i++)
			for (int j = 0; j < blockerMatrix[0].length; j++)
				this.mat[i][j] = blockerMatrix[i][j] ? BLOCKED : FREE;

		this.endRow = endRow;
		this.endCol = endCol;
	}

	public static Boolean[][] solution(boolean[][] blockerMatrix, int startRow, int startCol, int endRow, int endCol) {
		RatInMaze ratInMaze = new RatInMaze(blockerMatrix, startRow, startCol, endRow, endCol);

		ratInMaze.mat[startRow][startCol] = OCCUPIED;

		if (ratInMaze.hasSol(startRow, startCol))
			return ratInMaze.mat;

		return new Boolean[0][0];
	}

	private static String getString(String str) {
		return format("%-4s", str);
	}

	public static void print(Boolean[][] sol) {
		for (Boolean[] row : sol) {
			for (Boolean block : row)
				if (block == BLOCKED)
					out.print(getString("X"));
				else if (block == OCCUPIED)
					out.print(getString("*"));
				else
					out.print(getString("o"));
			out.println();
		}
	}

	private boolean hasSol(int r, int c) {
		if (r == endRow && c == endCol)
			return true;

		for (int[] move : nextMoves(r, c)) {
			int nextR = move[0] , nextC = move[1];
			mat[nextR][nextC] = OCCUPIED;

			if (!hasSol(nextR, nextC))
				mat[nextR][nextC] = FREE;
			else
				return true;
		}

		return false;
	}

	private List<int[]> nextMoves(int r, int c) {
		List<int[]> moves = new LinkedList<>();

		if (r + 1 < mat.length && mat[r + 1][c] == FREE)
			moves.add(new int[] { r + 1, c });

		if (r - 1 >= 0 && mat[r - 1][c] == FREE)
			moves.add(new int[] { r - 1, c });

		if (c + 1 < mat[0].length && mat[r][c + 1] == FREE)
			moves.add(new int[] { r, c + 1 });

		if (c - 1 >= 0 && mat[r][c - 1] == FREE)
			moves.add(new int[] { r, c - 1 });

		return moves;
	}
}
