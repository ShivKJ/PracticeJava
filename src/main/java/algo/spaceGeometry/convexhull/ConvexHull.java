package algo.spaceGeometry.convexhull;

import java.util.Collection;
import java.util.List;

import algo.spaceGeometry.XY;

public abstract class ConvexHull {
	protected final Collection<XY> input;

	@SuppressWarnings("unchecked")
	public ConvexHull(Collection<? extends XY> input) {
		this.input = (Collection<XY>) input;
	}

	public abstract List<XY> getConvexHull();

}
