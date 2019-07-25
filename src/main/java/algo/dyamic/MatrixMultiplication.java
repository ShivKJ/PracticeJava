package algo.dyamic;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;
import static java.lang.String.format;
import static java.util.Arrays.fill;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public final class MatrixMultiplication {
    private static final boolean PRINT_MATRIX = true;

    public static long combinationOfParamthesis(int n) {
        long[] p = new long[n];
        p[0] = 1;

        for (int i = 1; i < n; i++)
            for (int j = 0; j < i; j++)
                p[i] += p[j] * p[i - j - 1];

        return p[n - 1];
    }

    /**
     * dim is dimension of matrices.
     * 
     * if dim = [1,5,3,2]
     * then we have 3 matrices having dim
     * 1 X 5, 5 X 3, 3 X 2.
     * 
     * task is to find minimum number of multiplication
     * required when multiplying all such matrices.
     * 
     * @param dim
     * @return
     */
    public static int operations(int[] dim) {
        int n = dim.length - 1;
        int[][] dp = new int[n][n];

        stream(dp).forEach(p -> fill(p, MAX_VALUE));

        int out = operations(dim, dp, 0, n - 1);

        if (PRINT_MATRIX)
            print(dp);

        return out;
    }

    /**
     * dim is dimension of matrices.
     * 
     * if dim = [1,5,3,2]
     * then we have 3 matrices having dim
     * 1 X 5, 5 X 3, 3 X 2.
     * 
     * task is to find number of multiplication
     * from two matrices formed from combining matrices
     * from i to k and k+1 to j.
     * 
     * matrix[i].row    = dim[i]
     * matrix[i].column = dim[i+1] 
     * 
     * @param dim
     * @param i
     * @param k
     * @param j
     * @return
     */
    private static int matrixMult(int[] dim, int i, int k, int j) {
        return dim[i] * dim[k + 1] * dim[j + 1];
    }

    /**
     * finds number of multiplication required for 
     * multiplying i_th matrix to j_th one.
     *  
     * @param dim
     * @param dp
     * @param i
     * @param j
     * @return
     */
    private static int operations(int[] dim, int[][] dp, int i, int j) {
        if (dp[i][j] != MAX_VALUE)
            return dp[i][j];

        int out;

        if (i == j)
            out = 0;

        else {
            out = MAX_VALUE;

            for (int k = i; k < j; k++)
                out = min(out, operations(dim, dp, i, k) + operations(dim, dp, k + 1, j) + matrixMult(dim, i, k, j));

        }

        return dp[i][j] = out;
    }

    /**
     * @see #operations(int[])
     * @param dim
     * @return
     */
    public static int operationsBottomUp(int[] dim) {
        int n = dim.length - 1;
        int[][] dp = new int[n][n];

        stream(dp).forEach(p -> fill(p, MAX_VALUE));

        for (int j = 0; j < n; j++) {
            dp[j][j] = 0;

            for (int i = j - 1; i >= 0; i--)
                for (int k = i; k < j; k++)
                    dp[i][j] = min(dp[i][j], matrixMult(dim, i, k, j) + dp[i][k] + dp[k + 1][j]);
        }

        if (PRINT_MATRIX)
            print(dp);

        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        System.out.println(operations(new int[] { 30, 35, 15, 5, 10, 20, 25 }));
        System.out.println(operationsBottomUp(new int[] { 30, 35, 15, 5, 10, 20, 25 }));
    }

    private static void print(int[][] arr) {
        System.out.println("=====================Matrix============================");
        System.out.println(stream(arr).map(MatrixMultiplication::print).collect(joining("\n")));;
        System.out.println("=======================================================");
    }

    private static String print(int[] p) {
        return stream(p).mapToObj(MatrixMultiplication::print).collect(joining());
    }

    private static String print(int i) {
        return i == MAX_VALUE ? format("%-10s", "-") : format("%-10d", i);
    }

}
