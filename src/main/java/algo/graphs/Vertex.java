package algo.graphs;

import java.util.Set;

public interface Vertex<T> {
	T getData();

	int hashCode();

	boolean equals(Object obj);

	String toString();

	Set<Vertex<T>> adjacentVertices();

	default void addAdjacentVertex(Vertex<T> vertex) {
		adjacentVertices().add(vertex);
	}
}
