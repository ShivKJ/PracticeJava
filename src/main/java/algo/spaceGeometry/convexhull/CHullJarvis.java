package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Utils.bestPoint;
import static algo.spaceGeometry.Utils.getFarthestPoint;
import static java.util.Comparator.comparingDouble;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import algo.spaceGeometry.XY;

public abstract class CHullJarvis<E extends XY> extends CHullAlgo<E> {
	protected final E origin;

	public CHullJarvis(Collection<? extends XY> input) {
		super(input);
		this.origin = originPoint().orElse(null);
	}

	protected Optional<E> nextHullPoint(E src, E baseLine) {
		return nextHullPoint(src, baseLine, x -> !x.equals(src));
	}

	protected Optional<E> nextHullPoint(E src, E baseLine, Predicate<? super E> filter) {
		double BASE_LINE = baseLine.magnitude();

		Comparator<E> cosineComparator = comparingDouble((E p) -> {
			@SuppressWarnings("unchecked")
			E srcToP = (E) src.to(p);
			return srcToP.dotProduct(baseLine) / srcToP.magnitude() / BASE_LINE;
		});

		return bestPoint(input, filter, cosineComparator.thenComparingDouble(p -> src.to(p).magnitude()));
	}

	protected Optional<E> originPoint() {
		return getFarthestPoint(input, -180, comparingDouble(E::Y).reversed());
	}

}
