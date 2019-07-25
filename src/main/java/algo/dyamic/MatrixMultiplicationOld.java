package algo.dyamic;

import static java.lang.Integer.MAX_VALUE;
import static java.util.Arrays.fill;

public class MatrixMultiplicationOld {
    private final int[]   dims;
    private final boolean storeBracket;
    private int[][]       mat;
    private int[][]       brackets;
    private int           optimalMultiplication;

    public MatrixMultiplicationOld(int[] p, boolean storeBracket) {
        this.dims = p.clone();
        this.storeBracket = storeBracket;
        this.optimalMultiplication = -1;
    }

    public MatrixMultiplicationOld(int[] p) {
        this(p, false);
    }

    public MatrixMultiplicationOld solve() {
        this.mat = new int[dims.length][dims.length];

        for (int[] row : mat)
            fill(row, MAX_VALUE);

        if (storeBracket)
            this.brackets = new int[dims.length - 2][dims.length - 2];

        this.optimalMultiplication = get(1, dims.length - 1);

        return this;
    }

    public int[][] getBrackets() {
        return brackets;
    }

    public int getOptimalMultiplication() {
        return optimalMultiplication;
    }

    private int get(int i, int j) {
        if (mat[i][j] == MAX_VALUE) {

            int tmp = mat[i][j];

            for (int k = i; k < j; k++) {// i <=k < j
                tmp = get(i, k) + get(k + 1, j) + dims[i - 1] * dims[k] * dims[j];

                if (tmp < mat[i][j]) {
                    mat[i][j] = tmp;
                    if (storeBracket)
                        brackets[i - 1][j - 2] = k;
                }
            }
        }

        return mat[i][j];
    }

}
