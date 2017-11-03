package algo.graphs.traversal.mst;

import static java.util.Collections.emptyMap;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import algo.graphs.Edge;
import algo.graphs.Graph;
import algo.graphs.Vertex;

class MSTGraph<V extends Vertex, W extends Edge<? extends V>> implements Graph<V, W> {
	private final Collection<V>					vertices;
	private final Collection<W>					edges;
	private final Map<Integer, Map<Integer, W>>	edgeMapper;

	MSTGraph(Collection<V> vertices, Collection<W> edges) {
		this.vertices = vertices;
		this.edges = edges;
		this.edgeMapper = edges.stream().collect(groupingBy((W e) -> e.getSrc().uid(), toMap(e -> e.getDst().uid(), identity())));
	}

	@Override
	public Collection<V> vertices() {

		return vertices;
	}

	@Override
	public Collection<W> edges() {

		return edges;
	}

	@Override
	public Optional<W> edge(V src, V dst) {
		return ofNullable(edgeMapper.getOrDefault(src.uid(), emptyMap()).get(dst.uid()));
	}

	@Override
	public void connect(V from, V to) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<V> adjacentVertices(V vertex) {
		Collection<W> edgs = edgeMapper.getOrDefault(vertex.uid(), emptyMap()).values();
		Collection<V> vs = new ArrayList<>(edgs.size());

		for (W w : edgs)
			vs.add(w.getDst());

		return vs;
	}

	@Override
	public Collection<W> adjacentEdges(V vertex) {
		return edgeMapper.getOrDefault(vertex.uid(), emptyMap()).values();
	}

}
