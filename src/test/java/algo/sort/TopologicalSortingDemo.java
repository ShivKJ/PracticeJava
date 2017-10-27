package algo.sort;

import java.util.HashSet;
import java.util.Set;

import algo.graphs.Graph;
import algo.graphs.Vertex;
import algo.sorting.Sort;
import algo.sorting.TopologicalSorting;

public class TopologicalSortingDemo {
	public static void main(String[] args) {

		Graph<Vertex<String>> graph = new HashableGraph<>();
		Vertex<String> a = new HashableVertex<>("a");
		Vertex<String> b = new HashableVertex<>("b");
		Vertex<String> c = new HashableVertex<>("c");
		Vertex<String> d = new HashableVertex<>("d");
		Vertex<String> e = new HashableVertex<>("e");
		Vertex<String> f = new HashableVertex<>("f");
		Vertex<String> g = new HashableVertex<>("g");

		graph.addVertex(a);
		graph.addVertex(b);
		graph.addVertex(c);
		graph.addVertex(d);
		graph.addVertex(e);
		graph.addVertex(f);
		graph.addVertex(g);

		graph.connectVertices(a, b);
		graph.connectVertices(a, c);
		graph.connectVertices(b, c);
		Sort<String> sort = new TopologicalSorting<>(graph);
		System.out.println(sort.sort());
	}
}

class HashableGraph<T extends Vertex<?>> implements Graph<T> {
	private final Set<T> vertices;

	public HashableGraph() {
		this.vertices = new HashSet<>();
	}

	@Override
	public Set<T> getVertices() {

		return vertices;
	}

}

class HashableVertex<T> implements Vertex<T> {
	private final T					data;
	private final Set<Vertex<T>>	adja;

	public HashableVertex(T data) {
		this.data = data;
		this.adja = new HashSet<>();
	}

	@Override
	public T getData() {

		return data;
	}

	@Override
	public int hashCode() {

		return data.hashCode();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		return obj instanceof HashableVertex && ((HashableVertex<T>) obj).data.equals(data);
	}

	@Override
	public String toString() {

		return data.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Vertex<T>> adjacentVertices() {

		return adja;
	}

}
