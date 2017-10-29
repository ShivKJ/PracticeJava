package algo.graphs;

import java.util.Collection;
import java.util.Optional;

public interface Graph<V extends Vertex<?>, W extends Edge<? extends V>> {
	Collection<V> getVertices();

	Collection<W> edges();

	Optional<W> edge(V src, V dst);

	default void addVertex(V vertex) {
		getVertices().add(vertex);
	};

	void connect(V from, V to);

	Collection<V> adjacentVertices(V vertex);

	Collection<W> adjacentEdges(V vertex);

}
