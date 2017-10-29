package algo.graphs.traversal.mst;

import java.util.Queue;

public interface AdaptablePriorityQueue<E, P extends Comparable<P>> extends Queue<E> {
	void updatePriority(E e, P p);
}
