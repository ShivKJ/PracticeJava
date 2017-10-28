package algo.graphs;

import java.util.Collection;
import java.util.Optional;

public interface Graph<T extends Vertex<?>, E extends Edge<? extends T>> {
	Collection<T> getVertices();

	Collection<E> edges();

	Optional<E> getEdge(T src, T dst);

	default void addVertex(T vertex) {
		getVertices().add(vertex);
	};

	void connect(T from, T to);

	Collection<T> adjacentVertices(T vertex);

	Collection<E> adjacentEdges(T vertex);
}
