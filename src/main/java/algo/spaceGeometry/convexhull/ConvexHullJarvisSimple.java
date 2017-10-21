package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.XY.E2;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import algo.spaceGeometry.XY;

public class ConvexHullJarvisSimple extends ConvexHullJarvis {

	public ConvexHullJarvisSimple(Collection<XY> points) throws EmptyCollectionException {
		super(unmodifiableCollection(points));
	}

	@Override
	public List<XY> getConvexHull() {
		List<XY> convexHull = new LinkedList<>();

		XY origin = firstPoint() , src = origin , dst = null , baseLine = E2;
		convexHull.add(origin);

		Optional<XY> nextDST = null;

		while ((nextDST = nextHullPoint(src, baseLine)).isPresent() && !(dst = nextDST.get()).equals(origin)) {
			convexHull.add(dst);
			baseLine = src.to(dst);
			src = dst;
		}

		if (convexHull.size() > 1)
			convexHull.add(origin);// to close convex hull.

		return convexHull;
	}

}
