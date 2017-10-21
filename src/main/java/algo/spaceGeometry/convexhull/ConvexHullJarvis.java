package algo.spaceGeometry.convexhull;

import static java.util.Collections.min;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

import algo.spaceGeometry.XY;

public abstract class ConvexHullJarvis extends ConvexHull {

	public ConvexHullJarvis(Collection<XY> input) throws EmptyCollectionException {
		super(input);
	}

	protected Optional<XY> nextHullPoint(XY src, XY baseLine) {
		double BASE_LINE = baseLine.magnitude();

		Comparator<XY> cosineComparator = comparingDouble(p -> {
			XY srcToP = src.to(p);
			return srcToP.dotProduct(baseLine) / srcToP.magnitude() / BASE_LINE;
		});

		return input.stream()
				.filter(x -> !x.equals(src))
				.max(cosineComparator);
	}

	protected XY firstPoint() {
		return min(input, comparing(XY::X));
	}

}
