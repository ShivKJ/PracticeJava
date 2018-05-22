package algo.dynamics;

import algo.dyamic.MatrixMultiplication;

public class MatrixMultiplicationTest {
	public static void main(String[] args) {
		int[] dims = { 30, 35, 15, 5, 10, 20, 25 };
		MatrixMultiplication matrixMultiplication = new MatrixMultiplication(dims).solve();
		System.out.println(matrixMultiplication.getOptimalMultiplication());
	}
}
