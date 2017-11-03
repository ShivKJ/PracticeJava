package algo.graphs;

import static java.lang.Double.MAX_VALUE;

import java.util.Collection;
import java.util.Optional;

public interface Graph<V extends Vertex, W extends Edge<? extends V>> {
	Collection<V> vertices();

	Collection<W> edges();

	Optional<W> edge(V src, V dst);

	default double distance(V src, V dst) {
		Optional<W> optional = edge(src, dst);

		return optional.isPresent() ? optional.get().distance() : MAX_VALUE;
	}

	default void addVertex(V vertex) {
		vertices().add(vertex);
	};

	void connect(V from, V to);

	Collection<V> adjacentVertices(V vertex);

	Collection<W> adjacentEdges(V vertex);

}
