package algo.graphs;

public interface Vertex {

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	@Override
	String toString();

	int uid();

}
