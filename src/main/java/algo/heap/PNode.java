package algo.heap;

public interface PNode<E, P extends Comparable<P>> extends DataWrapper<E> {

	void setPriority(Object p);

	P getPriority();

}
