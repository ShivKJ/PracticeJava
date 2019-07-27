package algo.backtracking;

import static java.lang.String.format;
import static java.lang.System.out;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.util.LinkedList;
import java.util.List;

public class EulerKnightProblem {
    private static final int[][] DIRS = {
        { +1, +2 },
        { -1, +2 },

        { +2, +1 },
        { +2, -1 },

        { +1, -2 },
        { -1, -2 },

        { -2, +1 },
        { -2, -1 }
    };

    private final int[][] movement;
    private final int     totalSteps;
    private final int     N;

    private EulerKnightProblem(int n) {
        this.N = n;
        this.totalSteps = n * n;
        this.movement = new int[n][n];

    }

    public static int[][] solve(int startRow, int startCol, int n) {
        if (startCol >= n || startCol >= n)
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
            int nextR = move[0], nextC = move[1];
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

        for (int[] dir : DIRS) {
            int x = dir[0] + r, y = dir[1] + c;

            if (isValid(x, y) && movement[x][y] == 0)
                moves.add(new int[] { x, y });
        }

        return moves;
    }

    private boolean isValid(int i) {
        return i < N && i > -1;
    }

    private boolean isValid(int i, int j) {
        return isValid(i) && isValid(j);
    }

    public static void print(int[][] steps) {
        stream(steps).map(EulerKnightProblem::print)
                     .forEach(out::println);
    }

    private static String print(int[] row) {
        return stream(row).mapToObj(y -> format("%-5d", y))
                          .collect(joining());
    }

}
