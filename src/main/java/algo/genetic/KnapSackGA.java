package algo.genetic;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.EvolutionStatistics.ofNumber;
import static io.jenetics.engine.Limits.bySteadyFitness;
import static java.lang.Integer.parseInt;
import static java.nio.file.Files.newBufferedReader;
import static java.nio.file.Paths.get;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collector;

import org.slf4j.Logger;

import algo.dyamic.KnapSack01.KnapSackItem;

import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.Mutator;
import io.jenetics.Phenotype;
import io.jenetics.RouletteWheelSelector;
import io.jenetics.SinglePointCrossover;
import io.jenetics.TournamentSelector;
import io.jenetics.UniformCrossover;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.ISeq;

public class KnapSackGA {
	private static final Logger		LOGGER	= getLogger(KnapSackGA.class);
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

	public static Phenotype<BitGene, Integer> run(KnapSackItem[] items, int capacity) {
		return new KnapSackGA(items, capacity).solve();
	}

	public static Phenotype<BitGene, Integer> run(String file) throws IOException {
		try (BufferedReader reader = newBufferedReader(get(file))) {
			int capacity = parseInt(reader.readLine().split("\\s+")[1]);

			KnapSackItem[] items = reader.lines()
			                             .map(KnapSackGA::getItem)
			                             .toArray(KnapSackItem[]::new);
			return run(items, capacity);
			
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

	private Integer fitness(ISeq<KnapSackItem> items) {
		KnapSackItem item = items.stream().collect(toSum());
		return capacity > item.weight() ? item.value() : capacity - item.weight();
	}

	private boolean genotypeValidator(Genotype<BitGene> x) {
		int picked = 0;
		Chromosome<BitGene> chromosome = x.getChromosome();

		for (int i = 0; i < items.length; i++)
			if (chromosome.getGene(i).booleanValue()) {
				picked += items[i].weight();
				if (picked > capacity)
					return false;
			}

		return true;
	}

	private Phenotype<BitGene, Integer> solve() {
		Engine<BitGene, Integer> engine = Engine.builder(this::fitness, Codecs.ofSubSet(ISeq.of(items)))
		                                        .maximizing()
		                                        .populationSize(1000)
		                                        .genotypeFactory(() -> Genotype.of(BitChromosome.of(items.length, 0.001)))
		                                        .survivorsSelector(new TournamentSelector<>(200))
		                                        .offspringSelector(new RouletteWheelSelector<>())
		                                        .genotypeValidator(this::genotypeValidator)
		                                        .alterers(new Mutator<>(0.01),
		                                                new SinglePointCrossover<>(0.01),
		                                                new UniformCrossover<>(0.01))
		                                        .build();

		EvolutionStatistics<Integer, ?> statistics = ofNumber();

		Phenotype<BitGene, Integer> sol = engine.stream()
		                                        .limit(bySteadyFitness(100))
		                                        .limit(500)
		                                        .peek(statistics)
		                                        .collect(toBestPhenotype());

		LOGGER.info("\n" + statistics.toString());

		return sol;
	}

}
