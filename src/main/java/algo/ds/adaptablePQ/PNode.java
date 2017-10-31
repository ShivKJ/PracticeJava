package algo.ds.adaptablePQ;

public interface PNode extends Comparable<PNode> {

	<P extends Comparable<P>> void setPriority(P p);

	<P extends Comparable<P>> P getPriority();

	@Override
	default int compareTo(PNode o) {
		return getPriority().compareTo(o.getPriority());
	}

}
