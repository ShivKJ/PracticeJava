package algo.graphs.traversal.mst;

import java.util.Queue;

public interface AdaptablePriorityQueue<E extends PNode> extends Queue<E> {
	<P extends Comparable<P>> void updatePriority(E e, P p);

}
