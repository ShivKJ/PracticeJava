package simulation;

import static java.lang.Math.random;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

public class Utils {
    private static final int MAX_SAMPLE = 1_000_000;

    public static double monteCarloIntegration(DoubleUnaryOperator function) {
        return monteCarloIntegration(function, MAX_SAMPLE);
    }

    public static double monteCarloIntegration(DoubleUnaryOperator function, int k) {
        return DoubleStream.generate(Math::random)
                           .limit(k)
                           .map(function)
                           .average()

                           .getAsDouble();
    }

    public static double monteCarloIntegration(DoubleUnaryOperator function, double a, double b, int k) {
        return monteCarloIntegration(t -> (b - a) * function.applyAsDouble(a + t * (b - a)), k);
    }

    public static double monteCarloIntegrationToInf(DoubleUnaryOperator function, double a, int k) {
        return monteCarloIntegration(t -> function.applyAsDouble(a + 1 / t - 1) / (t * t), k);
    }

    public static double monteCarloDoubleIntegration(DoubleBinaryOperator fn, int k) {
        return DoubleStream.generate(() -> fn.applyAsDouble(random(), random()))
                           .limit(k)
                           .average()
                           .getAsDouble();
    }
}
