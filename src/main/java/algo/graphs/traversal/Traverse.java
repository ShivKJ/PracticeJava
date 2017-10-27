package algo.graphs.traversal;

import java.util.List;
import java.util.function.Consumer;

import algo.graphs.Graph;

public abstract class Traverse<T> {
	protected final Graph<? extends TraversalVertex<T>>	graph;
	protected final Consumer<TraversalVertex<T>>		vertexProcessor;

	public Traverse(Graph<? extends TraversalVertex<T>> graph, Consumer<TraversalVertex<T>> vertexProcessor) {
		this.graph = graph;
		this.vertexProcessor = vertexProcessor;
	}

	public abstract List<TraversalVertex<T>> traverse();
}
