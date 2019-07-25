package algo.genetic;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.EvolutionStatistics.ofNumber;
import static io.jenetics.engine.Limits.bySteadyFitness;
import static java.lang.Math.abs;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Collection;
import java.util.function.Function;

import org.slf4j.Logger;

import io.jenetics.EnumGene;
import io.jenetics.Mutator;
import io.jenetics.PartiallyMatchedCrossover;
import io.jenetics.Phenotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Codecs;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

public class SubSetGA implements Problem<ISeq<Integer>, EnumGene<Integer>, Integer> {
    private final static Logger LOGGER = getLogger(SubSetGA.class);
    private final ISeq<Integer> candidates;
    private final int           setSize;

    public SubSetGA(Collection<Integer> items, int size) {
        this.candidates = ISeq.of(items);
        this.setSize = size;

    }

    @Override
    public Function<ISeq<Integer>, Integer> fitness() {
        return sol -> abs(sol.stream().mapToInt(Integer::valueOf).sum());
    }

    @Override
    public Codec<ISeq<Integer>, EnumGene<Integer>> codec() {
        return Codecs.ofSubSet(candidates, setSize);
    }

    public Phenotype<EnumGene<Integer>, Integer> run() {
        EvolutionStatistics<Integer, ?> statistics = ofNumber();
        Phenotype<EnumGene<Integer>, Integer> res = Engine.builder(this)
                                                          .populationSize(100)
                                                          .minimizing()
                                                          .alterers(new PartiallyMatchedCrossover<>(0.1), new Mutator<>(0.1))
                                                          .build()
                                                          .stream()
                                                          .limit(bySteadyFitness(100))
                                                          .peek(statistics)
                                                          .collect(toBestPhenotype());
        LOGGER.info("\n" + statistics.toString());
        return res;
    }
}
