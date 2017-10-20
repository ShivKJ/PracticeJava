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
		Optional<XY> optionalP = nextHullPoint(o, baseLine);

		if (optionalP.isPresent()) {
			XY p = optionalP.get();
			convexHull.add(p);

			baseLine = o.getLine(p);

			while (!p.equals(o)) {
				XY x = nextHullPoint(p, baseLine).get();
				convexHull.add(x);
				baseLine = p.getLine(x);
				p = x;
			}
		}
		return convexHull;
	}

	private Optional<XY> nextHullPoint(XY source, XY baseLine) {
		double AB = baseLine.magnitude();

		Comparator<XY> cosineComparator = comparingDouble(c -> {
			XY ac = source.getLine(c);
			return ac.dotProduct(baseLine) / ac.magnitude() / AB;
		});

		return input.stream()
				.filter(x -> !x.equals(source))
				.max(cosineComparator);
	}

	public static class EmptyCollectionException extends Exception {

		private static final long serialVersionUID = -4143528867448348262L;

		public EmptyCollectionException(String message) {
			super(message);
		}
	}
}
