package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Utils.bestPoint;
import static algo.spaceGeometry.Utils.getFarthestPoint;
import static java.util.Comparator.comparingDouble;

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
		});;
		return bestPoint(input, filter, cosineComparator.thenComparingDouble(p -> src.to(p).magnitude()));
	}

	protected XY originPoint() {
		return getFarthestPoint(input, -180, comparingDouble(XY::Y).reversed()).get();
	}

}
