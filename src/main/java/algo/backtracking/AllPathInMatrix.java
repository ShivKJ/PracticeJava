package algo.backtracking;

import static java.lang.String.format;
import static java.lang.System.out;

import java.util.LinkedList;
import java.util.List;

public class AllPathInMatrix {
    private static final Boolean      RIGHT = true, DOWN = false;
    private final int                 m, n;
    private final List<List<Boolean>> paths;

    private AllPathInMatrix(int m, int n) {
        this.m = m - 1;
        this.n = n - 1;
        this.paths = new LinkedList<>();
    }

    public static List<List<Boolean>> paths(int m, int n) {
        AllPathInMatrix matrix = new AllPathInMatrix(m, n);

        List<Boolean> path = new LinkedList<>();
        matrix.paths.add(path);
        matrix.path(0, 0, path);

        return matrix.paths;

    }

    private void path(int r, int c, List<Boolean> path) {
        if (r < m) {
            if (c == n)
                while (r++ < m)
                    path.add(DOWN);
            else {
                List<Boolean> down = new LinkedList<>(path);
                paths.add(down);

                down.add(DOWN);
                path(r + 1, c, down);
            }
        }

        if (c < n) {
            if (r == m)
                while (c++ < n)
                    path.add(RIGHT);
            else {
                path.add(RIGHT);
                path(r, c + 1, path);
            }
        }
    }

    public static void print(int cols, List<Boolean> path) {
        out.print(format("%-4d", 1));

        int x = 1;

        for (Boolean boolean1 : path)
            out.print(format("%-4d", boolean1 == RIGHT ? ++x : (x = x + cols)));

        out.println();
    }
}
