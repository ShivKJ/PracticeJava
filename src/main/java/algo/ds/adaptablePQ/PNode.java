package algo.ds.adaptablePQ;

public interface PNode<E, P extends Comparable<P>> {

	void setPriority(Object p);

	P getPriority();

	E getData();

}
