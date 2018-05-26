package algo.ga;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Random;

import algo.genetic.SubSetGA;

public class SubSetSum {
	public static void main(String[] args) {
		Collection<Integer> ints = new Random(10L).ints(1000, -10, 20)
		                                          .filter(x -> x != 0)
		                                          .limit(10)
		                                          .boxed()
		                                          .collect(toList());
		System.out.println(new SubSetGA(ints, 5).run());
	}
}
