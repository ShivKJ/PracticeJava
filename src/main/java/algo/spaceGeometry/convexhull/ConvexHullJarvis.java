package algo.spaceGeometry.convexhull;

import static java.util.Collections.min;
import static java.util.Comparator.comparingDouble;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import algo.spaceGeometry.XY;

public abstract class ConvexHullJarvis extends ConvexHull {
	protected final XY origin;

	public ConvexHullJarvis(Collection<XY> input) throws EmptyCollectionException {
		super(input);
		this.origin = originPoint();
	}

	protected Optional<XY> nextHullPoint(XY src, XY baseLine) {
		return nextHullPoint(src, baseLine, x -> !x.equals(src));
	}

	protected Optional<XY> nextHullPoint(XY src, XY baseLine, Predicate<XY> filter) {
		double BASE_LINE = baseLine.magnitude();

		Comparator<XY> cosineComparator = comparingDouble((XY p) -> {
			XY srcToP = src.to(p);
			return srcToP.dotProduct(baseLine) / srcToP.magnitude() / BASE_LINE;
		});

		return bestPoint(filter, cosineComparator.thenComparingDouble(p -> src.to(p).magnitude()));
	}

	protected Optional<XY> bestPoint(Predicate<XY> filter, Comparator<XY> comp) {
		return input.stream().filter(filter).max(comp);
	}

	protected XY originPoint() {
		return min(input, comparingDouble(XY::X).thenComparingDouble(XY::Y));
	}
}
