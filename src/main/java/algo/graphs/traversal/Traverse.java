package algo.graphs.traversal;

import java.util.List;

import algo.graphs.Graph;
import algo.graphs.Vertex;

public abstract class Traverse<T> {
	Graph<? extends Vertex<T>> graph;

	public Traverse(Graph<? extends Vertex<T>> graph) {
		this.graph = graph;
	}

	public abstract List<Vertex<T>> traverse();

}
