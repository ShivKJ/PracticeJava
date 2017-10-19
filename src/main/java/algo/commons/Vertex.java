package algo.commons;

import java.util.Set;

public interface Vertex<T> {
	T getData();

	int hashCode();

	boolean equals(Object obj);

	Set<Vertex<T>> adjacentVertices();

	default void addAdjacentVertex(Vertex<T> vertex) {
		adjacentVertices().add(vertex);
	}
}
