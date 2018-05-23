package algo.ga;

import static java.lang.System.out;

import algo.genetic.MaximizeFunction;

public class MaximizingFunctionTest {
	public static void main(String[] args) {
		MaximizeFunction a = new MaximizeFunction(0, 1, x -> -x * Math.log(x));
		out.println(a.solve());
	}
}
