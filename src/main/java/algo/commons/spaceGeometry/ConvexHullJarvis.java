package algo.commons.spaceGeometry;

import static java.util.Collections.addAll;
import static java.util.Collections.min;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.ToDoubleFunction;

public class ConvexHullJarvis {

	private final Collection<XY> input;

	public ConvexHullJarvis(Collection<XY> points) {
		this.input = unmodifiableCollection(points);
	}

	public List<XY> getHull() {
		if (input.size() <= 2)
			return new LinkedList<>();

		List<XY> convexHull = new LinkedList<>();

		XY[] firstTwoPoints = getFirstTwoPoints();
		addAll(convexHull, firstTwoPoints);

		XY o = firstTwoPoints[0] , p = firstTwoPoints[1] , baseLine = o.getLine(p);

		while (!p.equals(o)) {
			XY nextHullPoint = getNextPoint(p, comparingDouble(cosine(p, baseLine)));
			convexHull.add(nextHullPoint);
			baseLine = p.getLine(nextHullPoint);
			p = nextHullPoint;
		}

		return convexHull;
	}

	private XY getNextPoint(XY removePoint, Comparator<XY> comparator) {
		return input.stream()
				.filter(x -> !x.equals(removePoint))
				.max(comparator)
				.get();
	}

	private static ToDoubleFunction<XY> sine(XY o) {
		return p -> {
			XY op = o.getLine(p);
			return op.y / op.magnitude();
		};
	}

	private static ToDoubleFunction<XY> cosine(XY a, XY ab) {
		double AB = ab.magnitude();

		return c -> {
			XY ac = a.getLine(c);
			return ac.dotProduct(ab) / ac.magnitude() / AB;
		};
	}

	private XY[] getFirstTwoPoints() {
		XY o = min(input, comparing(XY::X));
		XY p = getNextPoint(o, comparingDouble(sine(o)));

		return new XY[] { o, p };
	}

}
