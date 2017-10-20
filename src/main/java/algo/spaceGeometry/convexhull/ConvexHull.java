package algo.spaceGeometry.convexhull;

import static java.util.Collections.min;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import algo.spaceGeometry.XY;

public abstract class ConvexHull {
	protected final Collection<XY>	input;
	protected final XY				origin;

	public ConvexHull(Collection<XY> input) throws EmptyCollectionException {
		if (input.isEmpty())
			throw new EmptyCollectionException("input can not be empty.");

		this.input = input;
		this.origin = firstPoint();
	}

	public abstract List<XY> getConvexHull();

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
