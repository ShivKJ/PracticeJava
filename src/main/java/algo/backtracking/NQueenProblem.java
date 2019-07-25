package algo.backtracking;

import static java.lang.Math.abs;
import static java.lang.System.out;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author ShivKJ
 *
 */
public class NQueenProblem {
    /**
     * In N-Queen problem, we find position of N queen on N * N chess such that
     * no two queen attach each other.
     * 
     * Solution can be addressed using Backtracking strategy wherein we can go 
     * back a few steps and perturb some positions and proceed.
     * 
     */

    private boolean      exploreAllSolution;	// flag for whether or not to explore all solutions.
    private List<byte[]> solutions;			// keeps all solution if flag exploreAllSolution is True, else one solution

    private NQueenProblem(boolean exploreAllSolution) {
        this.exploreAllSolution = exploreAllSolution;
        this.solutions = exploreAllSolution ? new LinkedList<>() : new ArrayList<>(1);
    }

    public static List<byte[]> solution(int n, boolean allSolutions) {
        NQueenProblem problem = new NQueenProblem(allSolutions);
        problem.hasSolution(new byte[n], 0);

        return unmodifiableList(problem.solutions);
    }

    /**
     * "pos" stores positions of queens up to rows 0 to row -1.
     *  This method checks if a queen can be placed in a column
     *  on row "row". Of course this can only be determined only
     *  if we have placed Nth queen on the chess successfully.
     *  Therefore we place rowth Queen on row 'row' in feasible 
     *  column and check if this can lead to feasible solution
     *  using recursion. If this does not lead to a solution then
     *  we track back to find next feasible column.
     *  
     *  Notice that if we have found the solution and exploreAllSolution
     *  is true then we do not return true, instead we return false
     *  so that next possible solution is searched. Meanwhile we cache the 
     *  solution to a list 'solutions'.
     *   
     * @param pos
     * @param row
     * @return
     */
    private boolean hasSolution(byte[] pos, int row) {
        if (pos.length == row) {
            solutions.add(pos.clone());

            return !exploreAllSolution;
        }

        for (int col = 0; col < pos.length; col++) {
            boolean safe = true;

            for (int r = 0; r < row; r++)
                if (pos[r] == col || abs(col - pos[r]) == abs(r - row)) {
                    // to check if col_Th column on row_th row is feasible to put a queen. If not then next column is checked.
                    safe = false;
                    break;
                }

            if (safe) {
                pos[row] = (byte) col;
                if (hasSolution(pos, row + 1))// once a column is found on row then searching to place a queen on row + 1
                    return true;
            }

        }

        return false;
    }

    public static void print(byte[] pos) {
        for (byte i : pos) {
            for (int k = 0; k < pos.length; k++)
                out.print((k == i ? 'Q' : '*') + "  ");
            out.println();
        }
        out.println();
    }
}
