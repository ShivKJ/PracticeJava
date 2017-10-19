package algo.commons;

import java.util.Set;

public interface Graph<T> {
	Set<Vertex<T>> getVertices();

	default void addVertex(Vertex<T> vertex) {
		getVertices().add(vertex);
	}

	default void connectVertices(Vertex<T> from, Vertex<T> to) {
		from.addAdjacentVertex(to);
	};
}
