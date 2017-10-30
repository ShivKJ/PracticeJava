package algo.graphs.traversal.mst;

public interface PNode {
	public <P extends Comparable<P>> P getPriority();

	public <P extends Comparable<P>> void setPriority(P p);
}
