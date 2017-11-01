package algo.ds.adaptablePQ;

public interface PNode<E, P extends Comparable<P>> extends Comparable<PNode<?, P>> {

	void setPriority(Object p);

	P getPriority();

	@Override
	default int compareTo(PNode<?, P> o) {
		return getPriority().compareTo(o.getPriority());
	}

	E getData();

}
