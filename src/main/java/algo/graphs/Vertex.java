package algo.graphs;

import java.util.Set;

public interface Vertex<T> {
	T getData();

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	@Override
	String toString();

	<E extends Vertex<T>> Set<E> adjacentVertices();

	default <E extends Vertex<T>> void addAdjacentVertex(E vertex) {
		adjacentVertices().add(vertex);
	}
}
