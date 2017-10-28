package algo.graphs;

public interface Vertex<T> {
	public T getData();

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	@Override
	String toString();

}
