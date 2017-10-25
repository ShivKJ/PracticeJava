package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.PointUtils.dotProduct;
import static algo.spaceGeometry.PointUtils.line;
import static algo.spaceGeometry.Utils.best;
import static algo.spaceGeometry.Utils.getFarthestPoint;
import static java.util.Comparator.comparingDouble;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

import algo.spaceGeometry.Point;

abstract class CHullJarvis<E extends Point> extends CHull<E> {
	protected final Point origin;

	public CHullJarvis(Collection<? extends Point> input) {
		super(input);
		this.origin = originPoint().orElse(null);
	}

	protected Optional<Point> nextHullPoint(Point src, Point baseLine) {
		return nextHullPoint(src, baseLine, x -> !x.equals(src));
	}

	protected Optional<Point> nextHullPoint(Point src, Point baseLine, Predicate<? super Point> filter) {
		double BASE_LINE = baseLine.magnitude();

		Comparator<Point> cosineComparator = comparingDouble((Point p) -> {

			Point srcToP = line(src, p);

			return dotProduct(srcToP, baseLine) / srcToP.magnitude() / BASE_LINE;
		});

		return best(input, filter, cosineComparator.thenComparingDouble(p -> line(src, p).magnitude()));
	}

	protected Optional<Point> originPoint() {
		return getFarthestPoint(input, -180, comparingDouble(Point::Y).reversed());
	}

}
