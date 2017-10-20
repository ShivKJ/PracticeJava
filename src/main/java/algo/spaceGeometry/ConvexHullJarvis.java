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

		XY origin = min(input, comparing(XY::X)) , src = origin , dst = null , baseLine = E2;

		convexHull.add(origin);
		Optional<XY> optionalP = nextHullPoint(src, baseLine);

		while (optionalP.isPresent() && !(dst = optionalP.get()).equals(origin)) {
			convexHull.add(dst);
			baseLine = src.to(dst);
			src = dst;
			optionalP = nextHullPoint(src, baseLine);
		}

		if (convexHull.size() > 1)
			convexHull.add(origin);// to close convex hull.

		return convexHull;
	}

	private Optional<XY> nextHullPoint(XY src, XY baseLine) {
		double BASE_LINE = baseLine.magnitude();

		Comparator<XY> cosineComparator = comparingDouble(p -> {
			XY srcToP = src.to(p);
			return srcToP.dotProduct(baseLine) / srcToP.magnitude() / BASE_LINE;
		});

		return input.stream()
				.filter(x -> !x.equals(src))
				.max(cosineComparator);
	}

	public static class EmptyCollectionException extends Exception {

		private static final long serialVersionUID = -4143528867448348262L;

		public EmptyCollectionException(String message) {
			super(message);
		}
	}
}
