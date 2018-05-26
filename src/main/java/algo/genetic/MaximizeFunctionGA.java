package algo.genetic;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.EvolutionStatistics.ofNumber;
import static io.jenetics.engine.Limits.bySteadyFitness;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.function.DoubleUnaryOperator;

import org.slf4j.Logger;

import io.jenetics.DoubleChromosome;
import io.jenetics.DoubleGene;
import io.jenetics.Genotype;
import io.jenetics.MeanAlterer;
import io.jenetics.Mutator;
import io.jenetics.Phenotype;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.util.DoubleRange;

public class MaximizeFunctionGA {
	private static final Logger			LOGGER	= getLogger(MaximizeFunctionGA.class);
	private final double				min , max;
	private final DoubleUnaryOperator	function;

	public MaximizeFunctionGA(double min, double max, DoubleUnaryOperator function) {
		this.min = min;
		this.max = max;
		this.function = function;
	}

	public Phenotype<DoubleGene, Double> solve() {
		Engine<DoubleGene, Double> engine = Engine.builder(function::applyAsDouble, Codecs.ofScalar(DoubleRange.of(min, max)))
		                                          .populationSize(3000)
		                                          .genotypeFactory(() -> Genotype.of(DoubleChromosome.of(min, max)))
		                                          .maximizing()
		                                          .alterers(new Mutator<>(0.001), new MeanAlterer<>(0.001))
		                                          .build();

		EvolutionStatistics<Double, ?> statistics = ofNumber();

		Phenotype<DoubleGene, Double> best = engine.stream()
		                                           .limit(bySteadyFitness(50))
		                                           .limit(100)
		                                           .peek(statistics)
		                                           .collect(toBestPhenotype());
		LOGGER.info("\n" + statistics.toString());
		return best;
	}

}
