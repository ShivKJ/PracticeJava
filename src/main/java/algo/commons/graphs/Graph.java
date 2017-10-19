package algo.commons.graphs;

import java.util.Set;

public interface Graph<T extends Vertex<?>> {
	Set<T> getVertices();

	default void addVertex(T vertex) {
		getVertices().add(vertex);
	}

	default <E> void connectVertices(Vertex<E> from, Vertex<E> to) {
		from.addAdjacentVertex(to);
	}
}
