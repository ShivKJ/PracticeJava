package algo.ds.adaptablePQ;

import java.util.Queue;

public interface AdaptablePriorityQueue<E extends PNode<?, ? extends Comparable<?>>> extends Queue<E> {
	<P extends Comparable<P>> void updatePriority(E e, P p);

}
