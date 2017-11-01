package algo.graphs;

public interface Vertex<T> extends DataWrapper<T> {

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	@Override
	String toString();

	int uid();

}
