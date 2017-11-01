package algo.ds.adaptablePQ;

import algo.graphs.DataWrapper;

public interface PNode<E, P extends Comparable<P>> extends DataWrapper<E> {

	void setPriority(Object p);

	P getPriority();

	@Override
	E getData();

}
