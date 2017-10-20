package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.XY.E2;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import algo.spaceGeometry.XY;

public class ConvexHullJarvis extends ConvexHull {

	public ConvexHullJarvis(Collection<XY> points) throws EmptyCollectionException {
		super(unmodifiableCollection(points));
	}

	@Override
	public List<XY> getConvexHull() {
		List<XY> convexHull = new LinkedList<>();
		convexHull.add(origin);

		XY src = origin , dst = null , baseLine = E2;

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
}
