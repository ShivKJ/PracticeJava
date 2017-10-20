package algo.spaceGeometry;

import static algo.spaceGeometry.XY.E2;
import static java.util.Collections.min;
import static java.util.Collections.unmodifiableCollection;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.ToDoubleFunction;

public class ConvexHullJarvis {

	private final Collection<XY> input;

	public ConvexHullJarvis(Collection<XY> points) {
		this.input = unmodifiableCollection(points);
	}

	public List<XY> getConvexHull() throws EmptyCollectionException {
		if (input.isEmpty())
			throw new EmptyCollectionException("input can not be empty.");

		List<XY> convexHull = new LinkedList<>();

		XY o = min(input, comparing(XY::X));
		convexHull.add(o);

		XY baseLine = E2;
		Optional<XY> optionalP = nextHullPoint(o, comparingDouble(cosine(o, baseLine)));

		if (optionalP.isPresent()) {
			XY p = optionalP.get();
			convexHull.add(p);

			baseLine = o.getLine(p);

			while (!p.equals(o)) {
				XY x = nextHullPoint(p, comparingDouble(cosine(p, baseLine))).get();
				convexHull.add(x);
				baseLine = p.getLine(x);
				p = x;
			}
		}

		return convexHull;
	}

	private Optional<XY> nextHullPoint(XY removePoint, Comparator<XY> comparator) {
		return input.stream()
				.filter(x -> !x.equals(removePoint))
				.max(comparator);
	}

	private static ToDoubleFunction<XY> cosine(XY a, XY ab) {
		double AB = ab.magnitude();

		return c -> {
			XY ac = a.getLine(c);
			return ac.dotProduct(ab) / ac.magnitude() / AB;
		};
	}

	public static class EmptyCollectionException extends Exception {

		private static final long serialVersionUID = -4143528867448348262L;

		public EmptyCollectionException(String message) {
			super(message);
		}
	}
}
