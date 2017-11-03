package algo.graphs;

import static java.util.Collections.emptyList;
import static java.util.Optional.empty;

import java.util.Collection;
import java.util.Optional;

public final class Graphs {
	private Graphs() {}

	@SuppressWarnings("rawtypes")
	private static final Graph EMPTY_GRAPH = new EmptyGraph<>();

	@SuppressWarnings("unchecked")
	public static <V extends Vertex, W extends Edge<? extends V>> Graph<V, W> emptyGraph() {
		return EMPTY_GRAPH;
	}

	private static final class EmptyGraph<V extends Vertex, W extends Edge<? extends V>> implements Graph<V, W> {

		@Override
		public Collection<V> vertices() {

			return emptyList();
		}

		@Override
		public Collection<W> edges() {

			return emptyList();
		}

		@Override
		public Optional<W> edge(V src, V dst) {

			return empty();
		}

		@Override
		public void connect(V from, V to) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Collection<V> adjacentVertices(V vertex) {

			return emptyList();
		}

		@Override
		public Collection<W> adjacentEdges(V vertex) {

			return emptyList();
		}

	}
}
