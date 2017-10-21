package algo.spaceGeometry.convexhull;

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
import algo.spaceGeometry.XYHash;

public class ConvexHullJarvisOptimised extends ConvexHullJarvis {
	private final Set<XY>	convexHull;
	private XY				a , b;

	public ConvexHullJarvisOptimised(Collection<XY> input) throws EmptyCollectionException {
		super(input.stream().map(XYHash::new).collect(toSet()));
		this.convexHull = new LinkedHashSet<>();
		this.a = this.origin;
	}

	@Override
	public List<XY> getConvexHull() {
		convexHull.add(origin);
		XY baseLine = new XYHash(E2);

		Optional<XY> nextB = null;
		Predicate<XY> inTriangleOAB = this::pointInTriangleOAB;

		while ((nextB = nextHullPoint(a, baseLine)).isPresent() && (b = nextB.get()) != origin) {
			convexHull.add(b);
			input.removeIf(inTriangleOAB);
			baseLine = a.to(b);
			a = b;
		}

		return output();
	}

	@Override
	protected Optional<XY> nextHullPoint(XY src, XY baseLine) {
		return nextHullPoint(src, baseLine, x -> x != src);
	}

	@Override
	protected Optional<XY> bestPoint(Predicate<XY> filter, Comparator<XY> comp) {
		XY a = null;

		for (XY xy : input)
			if (filter.test(xy))
				if (a == null) {
					a = xy;
					continue;
				} else if (comp.compare(a, xy) < 0)
					a = xy;

		return ofNullable(a);
	}

	private List<XY> output() {
		List<XY> output = new ArrayList<>(convexHull);

		if (output.size() > 1)
			output.add(origin);// closing convex hull

		return output;
	}

	private boolean pointInTriangleOAB(XY x) {
		if (!convexHull.contains(x)) {
			XY xo = x.to(origin) ,
					xa = x.to(a) ,
					xb = x.to(b);

			boolean ab = isCrossProductPositive(xa, xb);

			return isCrossProductPositive(xo, xa) == ab && ab == isCrossProductPositive(xb, xo);
		}
		return false;
	}

	private static boolean isCrossProductPositive(XY a, XY b) {
		return a.X() * b.Y() > a.Y() * b.X();
	}
}
