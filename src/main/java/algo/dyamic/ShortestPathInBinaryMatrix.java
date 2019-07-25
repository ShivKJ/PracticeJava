package algo.dyamic;

import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathInBinaryMatrix {
    private static final int     EMPTY      = 0;
    private static final int[][] DIRECTIONS = {
        { +0, -1 },
        { +1, -1 },
        { +1, -0 },
        { +1, +1 },
        { +0, +1 },
        { -1, +1 },
        { -1, -0 },
        { -1, -1 }
    };

    public static int shortestPathBinaryMatrix(int[][] grid) {
        if (grid[0][0] == 1)
            return -1;

        int n = grid.length;

        if (grid[n - 1][n - 1] == 1)
            return -1;

        boolean[][] notVisited = createMatrix(grid);

        Queue<int[]> q = new LinkedList<>();

        q.add(new int[] { 0, 0 });
        notVisited[0][0] = false;

        int steps = 0;

        while (!q.isEmpty()) {

            steps++;// just for submission of program. actually this line of code should be in the end of loop.

            int size = q.size();

            for (int i = 0; i < size; i++) {
                int[] s = q.poll();

                if (reached(s, n))
                    return steps;

                for (int[] dir : DIRECTIONS) {
                    int x = s[0] + dir[0], y = s[1] + dir[1];

                    if (isValid(x, y, n) && notVisited[x][y]) {
                        q.add(new int[] { x, y });
                        notVisited[x][y] = false;
                    }
                }
            }

        }

        return -1;
    }

    private static boolean isValid(int x, int n) {
        return x < n && x > -1;
    }

    private static boolean isValid(int x, int y, int n) {
        return isValid(x, n) && isValid(y, n);
    }

    private static boolean reached(int[] xy, int n) {
        return xy[0] == n - 1 && xy[1] == n - 1;
    }

    private static boolean[][] createMatrix(int[][] grid) {
        int n = grid.length;

        boolean[][] out = new boolean[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                out[i][j] = grid[i][j] == EMPTY;

        return out;

    }

}
