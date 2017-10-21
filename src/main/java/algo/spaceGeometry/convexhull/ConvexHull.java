package algo.spaceGeometry.convexhull;

import java.util.Collection;
import java.util.List;

import algo.spaceGeometry.XY;

public abstract class ConvexHull {
	protected final Collection<? extends XY> input;

	public ConvexHull(Collection<? extends XY> input) throws EmptyCollectionException {
		if (input.isEmpty())
			throw new EmptyCollectionException("input can not be empty.");

		this.input = input;

	}

	public abstract List<XY> getConvexHull();

}
