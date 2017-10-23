package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.XY.E2;
import static java.util.Collections.unmodifiableCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import algo.spaceGeometry.XY;

public class ConvexHullJarvisSimple extends ConvexHullJarvis {

	public ConvexHullJarvisSimple(Collection<? extends XY> points) {
		super(unmodifiableCollection(points));
	}

	@Override
	public List<XY> getConvexHull() {
		List<XY> convexHull = new ArrayList<>();

		if (!input.isEmpty()) {
			convexHull.add(origin);

			XY src = origin , dst = null , baseLine = E2;

			Optional<XY> nextDST = null;

			while ((nextDST = nextHullPoint(src, baseLine)).isPresent()) {
				convexHull.add(dst = nextDST.get());

				if (dst.equals(origin))
					break;

				baseLine = src.to(dst);
				src = dst;
			}
		}

		return convexHull;
	}

}
