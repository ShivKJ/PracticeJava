package algo.sorting;

import static java.util.Collections.asLifoQueue;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import algo.graphs.Graph;
import algo.graphs.Vertex;

public class TopologicalSorting<T> implements Sort<T> {
	private final Graph<Vertex<T>, ?>	graph;
	private final Collection<Vertex<T>>	visitedNodes;
	private final Queue<Vertex<T>>		outputStack;

	@SuppressWarnings("unchecked")
	public TopologicalSorting(Graph<? extends Vertex<T>, ?> graph) {
		this.graph = (Graph<Vertex<T>, ?>) graph;
		this.visitedNodes = new HashSet<>();
		this.outputStack = asLifoQueue(new LinkedList<>());
	}

	@Override
	public List<T> sort() {
		for (Vertex<T> vertex : graph.getVertices())
			if (!visitedNodes.contains(vertex))
				processVertex(vertex);

		return outputStack.stream()
				.map(Vertex::getData)
				.collect(toList());
	}

	private void processVertex(Vertex<T> sourceVertex) {
		visitedNodes.add(sourceVertex);

		for (Vertex<T> adjacentVertex : graph.adjacentVertices(sourceVertex))
			if (!visitedNodes.contains(adjacentVertex))
				processVertex(adjacentVertex);

		outputStack.offer(sourceVertex);
	}
}
