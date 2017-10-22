package algo.spaceGeometry.convexhull;

import static java.util.Collections.min;
import static java.util.Comparator.comparingDouble;
import static java.util.Optional.ofNullable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import algo.spaceGeometry.XY;

public abstract class ConvexHullJarvis extends ConvexHull {
	protected final XY origin;

	public ConvexHullJarvis(Collection<? extends XY> input) throws EmptyCollectionException {
		super(input);
		this.origin = originPoint();
	}

	protected Optional<? extends XY> nextHullPoint(XY src, XY baseLine) {
		return nextHullPoint(src, baseLine, x -> !x.equals(src));
	}

	protected Optional<? extends XY> nextHullPoint(XY src, XY baseLine, Predicate<XY> filter) {
		double BASE_LINE = baseLine.magnitude();

		Comparator<XY> cosineComparator = comparingDouble((XY p) -> {
			XY srcToP = src.to(p);
			return srcToP.dotProduct(baseLine) / srcToP.magnitude() / BASE_LINE;
		});

		return bestPoint(filter, cosineComparator.thenComparingDouble(p -> src.to(p).magnitude()));
	}

	protected Optional<? extends XY> bestPoint(Predicate<? super XY> filter, Comparator<? super XY> comp) {
		XY bestPoint = null;

		for (XY currentPoint : input)
			if (filter.test(currentPoint))
				if (bestPoint == null) {
					bestPoint = currentPoint;
					continue;
				} else if (comp.compare(bestPoint, currentPoint) < 0)
					bestPoint = currentPoint;

		return ofNullable(bestPoint);
	}

	protected XY originPoint() {
		return min(input, comparingDouble(XY::X).thenComparingDouble(XY::Y));
	}
}
