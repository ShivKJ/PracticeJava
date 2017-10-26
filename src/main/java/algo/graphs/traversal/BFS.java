package algo.graphs.traversal;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import algo.graphs.Graph;
import algo.graphs.Vertex;

public class BFS<T> extends Traverse<T> {
	Map<Vertex<T>, LabeledVertex<T>> map;

	public BFS(Graph<? extends Vertex<T>> graph) {
		super(graph);
		this.map = new HashMap<>();
	}

	@Override
	public List<Vertex<T>> traverse() {
		if (graph.getVertices().isEmpty())
			return emptyList();

		LabeledVertex<T> s = new LabeledVertex<>(graph.getVertices().iterator().next());

		int level = 0;
		s.d = level;

		Queue<LabeledVertex<T>> queue = new LinkedList<>();
		queue.add(s);

		while (!queue.isEmpty()) {
			LabeledVertex<T> curr = queue.poll();
			level++;

			for (LabeledVertex<T> v : curr.childrens) {
				if (v.color == Color.WHITE) {
					v.color = Color.GREY;
					v.p = curr;
					v.d = level;
					queue.add(new LabeledVertex<>(v));
				}
			}
			curr.color = Color.BLACK;

		}

		return queue.stream().map(LabeledVertex<T>::getVertex).collect(toList());
	}

	private static enum Color {
		WHITE, GREY, BLACK
	}

	private final static class LabeledVertex<T> implements Vertex<T> {
		final Vertex<T>					vertex;
		@SuppressWarnings("unused")
		LabeledVertex<T>				p;
		Collection<LabeledVertex<T>>	childrens;
		Color							color;
		@SuppressWarnings("unused")
		int								d;

		public LabeledVertex(Vertex<T> vertex) {
			this(vertex, null, 0);
		}

		public LabeledVertex(Vertex<T> vertex, LabeledVertex<T> p, int d) {
			this.vertex = vertex;
			this.p = p;
			this.d = d;
			this.color = Color.WHITE;
			this.childrens = vertex.adjacentVertices()
					.stream()
					.map(LabeledVertex<T>::new)
					.collect(toList());
		}

		public Vertex<T> getVertex() {
			return vertex;
		}

		@Override
		public T getData() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Set<Vertex<T>> adjacentVertices() {

			return vertex.adjacentVertices();
		}

		@Override
		public int hashCode() {

			return vertex.hashCode();
		}

		@Override
		public boolean equals(Object obj) {

			return vertex.equals(obj);
		}

	}

}
