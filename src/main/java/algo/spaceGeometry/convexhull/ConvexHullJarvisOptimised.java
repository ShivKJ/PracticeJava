package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.PointLocation.OUTSIDE;
import static algo.spaceGeometry.XY.E2;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import algo.spaceGeometry.XY;
import algo.spaceGeometry.XYHashed;

public class ConvexHullJarvisOptimised extends ConvexHullJarvis {
	private final Set<XY>	convexHull;
	private XY				a , b;

	public ConvexHullJarvisOptimised(Collection<? extends XY> input) throws EmptyCollectionException {
		super(input.stream().map(XYHashed::new).collect(toSet()));
		this.convexHull = new LinkedHashSet<>();
		this.a = this.origin;
	}

	@Override
	public List<XY> getConvexHull() {
		convexHull.add(origin);
		XY baseLine = new XYHashed(E2);

		Optional<? extends XY> nextB = null;

		while ((nextB = nextHullPoint(a, baseLine)).isPresent() && (b = nextB.get()) != origin) {
			convexHull.add(b);
			input.removeIf(pointNotOutside(new LocationFindingTriangle(origin, a, b)));
			baseLine = a.to(b);
			a = b;
		}

		return output();
	}

	private Predicate<? super XY> pointNotOutside(LocationFindingTriangle triangle) {
		return x -> !convexHull.contains(x) && triangle.getPointLocation(x) != OUTSIDE;
	}

	@Override
	protected Optional<? extends XY> nextHullPoint(XY src, XY baseLine) {
		return nextHullPoint(src, baseLine, x -> x != src);
	}

	@Override
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

	private List<XY> output() {
		List<XY> output = new ArrayList<>(convexHull);

		if (output.size() > 1)
			output.add(origin);// closing convex hull

		return output;
	}

}
