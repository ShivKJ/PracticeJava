package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Point.E2;
import static algo.spaceGeometry.PointUtils.line;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Optional;

import algo.spaceGeometry.Point;

class CHullJarvisSimple<E extends Point> extends CHullJarvis<E> {

	public CHullJarvisSimple(Collection<? extends Point> points) {
		super(unmodifiableCollection(points));
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConvexHull<E> getConvexHull() {
		ConvexHull<E> convexHull = new ConvexHullImpl<>();

		if (!input.isEmpty()) {
			convexHull.add((E) origin);

			E src = (E) origin , dst = null;
			Point baseLine = E2;

			Optional<Point> nextDST = null;

			while ((nextDST = nextHullPoint(src, baseLine)).isPresent()) {
				convexHull.add(dst = (E) nextDST.get());

				if (dst.equals(origin))
					break;

				baseLine = line(src, dst);
				src = dst;
			}
		}

		return convexHull;
	}

}
