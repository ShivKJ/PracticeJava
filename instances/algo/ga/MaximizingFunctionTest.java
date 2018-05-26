package algo.ga;

import static java.lang.System.out;

import algo.genetic.MaximizeFunctionGA;

public class MaximizingFunctionTest {
	public static void main(String[] args) {
		MaximizeFunctionGA a = new MaximizeFunctionGA(0, 1, x -> -x * Math.log(x));
		out.println(a.solve());
	}
}
