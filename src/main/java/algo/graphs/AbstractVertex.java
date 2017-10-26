package algo.graphs;

import java.util.HashSet;
import java.util.Set;

public class AbstractVertex<T> implements Vertex<T> {
	private final T					data;
	private final Set<Vertex<T>>	adjacents;

	public AbstractVertex(T data) {
		this.data = data;
		this.adjacents = new HashSet<>();
	}

	@Override
	public T getData() {

		return data;
	}

	@Override
	public Set<Vertex<T>> adjacentVertices() {

		return adjacents;
	}

	@Override
	public void addAdjacentVertex(Vertex<T> vertex) {
		adjacents.add(vertex);
	}

	@Override
	public int hashCode() {
		return data.hashCode();
	}

	@Override
	public String toString() {
		return data.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vertex && ((Vertex<?>) obj).getData().equals(data);
	}
}
