package algo.genetic;

import static java.lang.Integer.parseInt;
import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collector;

import algo.dyamic.KnapSack01.KnapSackItem;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.Mutator;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SinglePointCrossover;
import io.jenetics.TournamentSelector;
import io.jenetics.UniformCrossover;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;
import io.jenetics.util.ISeq;

public class KnapSackGA {
	private final KnapSackItem[]	items;
	private final int				capacity;

	private KnapSackGA(KnapSackItem[] items, int capacity) {
		this.items = items;
		this.capacity = capacity;
	}

	private static KnapSackItem getItem(String line) {
		String[] split = line.split("\\s+");
		return new KnapSackItem(parseInt(split[1]), parseInt(split[0]));
	}

	private static void run(String file) throws IOException {
		try (BufferedReader reader = newBufferedReader(get(file))) {
			int capacity = parseInt(reader.readLine().split("\\s+")[1]);

			KnapSackItem[] items = reader.lines()
			                             .map(KnapSackGA::getItem)
			                             .toArray(KnapSackItem[]::new);
			KnapSackGA ga = new KnapSackGA(items, capacity);
			System.out.println(ga.solve());

		} catch (IOException e) {
			throw new IOException();
		}

	}

	private static Collector<KnapSackItem, ?, KnapSackItem> toSum() {
		return Collector.of(() -> new int[2],
		        (a, b) -> {
			        a[0] += b.weight();
			        a[1] += b.value();
		        },
		        (a, b) -> {
			        a[0] += b[0];
			        a[1] += b[1];
			        return a;
		        },
		        r -> new KnapSackItem(r[0], r[1]));
	}

	private static Function<ISeq<KnapSackItem>, Integer> fitness(int capacity) {
		return items -> {
			KnapSackItem item = items.stream().collect(toSum());
			return capacity > item.weight() ? item.value() : capacity - item.weight();
		};
	}

	private int solve() {
		Factory<Genotype<BitGene>> factory = () -> Genotype.of(BitChromosome.of(items.length, 0.001));;

		Engine<BitGene, Integer> engine = Engine.builder(fitness(capacity), Codecs.ofSubSet(ISeq.of(items)))
		                                        .maximizing()
		                                        .populationSize(1000)
		                                        .genotypeFactory(factory)
		                                        .survivorsSelector(new TournamentSelector<>(200))
		                                        .offspringSelector(new RouletteWheelSelector<>())
		                                        .alterers(new Mutator<>(0.01),
		                                                new SinglePointCrossover<>(0.01),
		                                                new UniformCrossover<>(0.01))
		                                        .build();

		EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();

		Phenotype<BitGene, Integer> solution = engine.stream()
		                                             .limit(Limits.bySteadyFitness(100))
		                                             .limit(500)
		                                             .peek(statistics)
		                                             .collect(EvolutionResult.toBestPhenotype());
		System.out.println(statistics);
		return solution.getFitness();
	}

	public static void main(String[] args) throws IOException {
		if (args.length == 0)
			args = new String[] { "data/knapsack/ks_10000_0.txt" };

		run(args[0]);
	}
}
