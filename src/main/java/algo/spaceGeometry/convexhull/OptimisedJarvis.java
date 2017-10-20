package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.XY.E2;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import algo.spaceGeometry.XY;
import algo.spaceGeometry.XYHash;

public class OptimisedJarvis extends ConvexHull {
	private final Set<XY>	convexHull;
	private XY				a , b;

	public OptimisedJarvis(Collection<XY> input) throws EmptyCollectionException {
		super(input.stream().map(XYHash::new).collect(toSet()));
		this.convexHull = new LinkedHashSet<>();
	}

	@Override
	public List<XY> getConvexHull() {
		convexHull.add(origin);

		a = origin;
		XY baseLine = new XYHash(E2);

		Optional<XY> optionalB = null;
		Predicate<XY> shouldRemove = shouldRemove();

		while ((optionalB = nextHullPoint(a, baseLine)).isPresent() && (b = optionalB.get()) != origin) {
			convexHull.add(b);
			input.removeIf(shouldRemove);
			baseLine = a.to(b);
			a = b;
		}

		return output();
	}

	private List<XY> output() {
		List<XY> output = new ArrayList<>(convexHull);

		if (output.size() != 1)
			output.add(origin);// closing convexhull

		return output;
	}

	private static boolean isPositive(XY a, XY b) {
		return a.X() * b.Y() > a.Y() * b.X();
	}

	private Predicate<XY> shouldRemove() {
		return x -> {
			if (!convexHull.contains(x)) {
				XY xo = x.to(origin) ,
						xa = x.to(a) ,
						xb = x.to(b);

				boolean oa = isPositive(xo, xa) ,
						ab = isPositive(xa, xb) ,
						bo = isPositive(xb, xo);

				return oa == ab && ab == bo;
			}
			return false;
		};
	}
}
