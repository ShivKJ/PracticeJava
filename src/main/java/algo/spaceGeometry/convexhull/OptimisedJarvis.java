package algo.spaceGeometry.convexhull;

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
	private Set<XY>	convexHull;
	private XY		a , b , baseLine;

	public OptimisedJarvis(Collection<XY> input) throws EmptyCollectionException {
		super(input.stream().map(XYHash::new).collect(toSet()));
	}

	@Override
	public List<XY> getConvexHull() {
		if (input.size() <= 2)
			return new ArrayList<>(input);

		convexHull = new LinkedHashSet<>();
		convexHull.add(origin);

		baseLine = new XYHash(XY.E2);
		a = nextHullPoint(origin, baseLine).get();
		convexHull.add(a);

		baseLine = origin.to(a);
		b = nextHullPoint(a, baseLine).get();
		convexHull.add(b);

		input.removeIf(shouldRemove());

		baseLine = a.to(b);
		Optional<XY> optionalB = nextHullPoint(b, baseLine);
		XY c = null;

		while (optionalB.isPresent() && (c = optionalB.get()) != origin) {
			baseLine = b.to(c);
			a = b;
			b = c;
			convexHull.add(b);

			input.removeIf(shouldRemove());
			optionalB = nextHullPoint(b, baseLine);
		}

		List<XY> output = new ArrayList<>(convexHull);
		output.add(origin);// closing convexhull

		return output;
	}

	public void initialize() {

	}

	private Predicate<XY> shouldRemove() {

		return x -> {
			if (convexHull.contains(x))
				return false;
			XY xa = x.to(origin) , xb = x.to(a) , xc = x.to(b);
			boolean ab = isPositive(xa, xb) , bc = isPositive(xb, xc) , ca = isPositive(xc, xa);
			return ab == bc && bc == ca;
		};
	}

	private static boolean isPositive(XY a, XY b) {
		return a.X() * b.Y() > a.Y() * b.X();
	}

}
