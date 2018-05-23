package algo.ga;

import java.io.IOException;

import algo.genetic.KnapSackGA;

import io.jenetics.BitGene;
import io.jenetics.Phenotype;

public class KnapSackGATest {
	public static void main(String[] args) throws IOException {
		if (args.length == 0)
			args = new String[] { "data/knapsack/ks_10000_0.txt" };

		Phenotype<BitGene, Integer> solution = KnapSackGA.run(args[0]);
		System.out.println(solution.getFitness());
	}
}
