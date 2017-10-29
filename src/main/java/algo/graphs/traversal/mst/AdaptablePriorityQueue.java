package algo.graphs.traversal.mst;

import java.util.Queue;

public interface AdaptablePriorityQueue<E> extends Queue<E> {
	void updatePriority(E e, Comparable<?> p);

}
