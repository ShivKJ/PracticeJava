package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.Point.E2;
import static algo.spaceGeometry.PointUtils.to;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Optional;

import algo.spaceGeometry.Boundary;
import algo.spaceGeometry.Point;

class CHullSimple<E extends Point> extends CHullJarvis<E> {

	public CHullSimple(Collection<? extends Point> points) {
		super(unmodifiableCollection(points));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boundary<E> getConvexHull() {
		Boundary<E> convexHull = new ConvexHull<>();

		if (!input.isEmpty()) {
			convexHull.add((E) origin);

			E src = (E) origin , dst = null;
			Point baseLine = E2;

			Optional<Point> nextDST = null;

			while ((nextDST = nextHullPoint(src, baseLine)).isPresent()) {
				convexHull.add(dst = (E) nextDST.get());

				if (dst.equals(origin))
					break;

				baseLine = to(src, dst);
				src = dst;
			}
		}

		return convexHull;
	}

}
