package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.XY.E2;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Optional;

import algo.spaceGeometry.Boundary;
import algo.spaceGeometry.XY;

class CHullSimple<E extends XY> extends CHullJarvis<E> {

	public CHullSimple(Collection<? extends XY> points) {
		super(unmodifiableCollection(points));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boundary<E> getConvexHull() {
		Boundary<E> convexHull = new ConvexHull<>();

		if (!input.isEmpty()) {
			convexHull.add(origin);

			E src = origin , dst = null , baseLine = (E) E2;

			Optional<E> nextDST = null;

			while ((nextDST = nextHullPoint(src, baseLine)).isPresent()) {
				convexHull.add(dst = nextDST.get());

				if (dst.equals(origin))
					break;

				baseLine = (E) src.to(dst);
				src = dst;
			}
		}

		return convexHull;
	}

}
