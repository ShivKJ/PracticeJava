package algo.spaceGeometry;

import java.util.List;
import java.util.Set;
import java.util.Spliterator;

public interface Boundary<E extends XY> extends Set<E>, List<E> {

	@Override
	default Spliterator<E> spliterator() {
		return List.super.spliterator();
	}

}
