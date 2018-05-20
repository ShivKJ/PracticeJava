package algo.backtracking;

public class RatInMazeTest {
	public static void main(String[] args) {
		/*
		 * This is the blocker matrix
		 * X = Blocked position
		 * o = Unblocked Position
		 * 
		 *	  o   X   X   X   
			  o   o   X   o   
		      X   o   X   X   
			  o   o   o   o   
		 */

		boolean[][] blocker = {
		    { false, true, true, true },
		    { false, false, true, false },
		    { true, false, true, true },
		    { false, false, false, false }
		};

		RatInMaze.print(RatInMaze.solution(blocker, 0, 0, 3, 3));
	}
}
