package algo.graphs.traversal;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import algo.graphs.Graph;
import algo.graphs.Vertex;

public class BFS<T> extends Traverse<T> {

	public BFS(Graph<? extends Vertex<T>> graph) {
		super(graph);
	}

	@Override
	public List<Vertex<T>> traverse() {
		if (graph.getVertices().isEmpty())
			return emptyList();

		return bfs(new LabeledVertex<>(graph.getVertices().iterator().next()));
	}

	private List<Vertex<T>> bfs(LabeledVertex<T> s) {

		int depth = 0;
		s.depth = depth;

		Queue<LabeledVertex<T>> queue = new LinkedList<>();
		queue.add(s);

		while (!queue.isEmpty()) {
			LabeledVertex<T> curr = queue.poll();
			depth++;

			for (LabeledVertex<T> v : curr.childrens) {
				if (v.color == Color.WHITE) {
					v.set(curr, depth, Color.GREY);
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
		LabeledVertex<T>				parent;
		Collection<LabeledVertex<T>>	childrens;
		Color							color;
		@SuppressWarnings("unused")
		int								depth;

		LabeledVertex(Vertex<T> vertex) {
			this.vertex = vertex;
			this.color = Color.WHITE;
			this.childrens = vertex.adjacentVertices()
					.stream()
					.map(LabeledVertex<T>::new)
					.collect(toList());
		}

		Vertex<T> getVertex() {
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

		void set(LabeledVertex<T> parent, int depth, Color color) {
			this.parent = parent;
			this.depth = depth;
			this.color = color;
		}
	}

}
