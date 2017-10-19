package algo.sorting.topologicalSorting;

import static java.util.Collections.asLifoQueue;
import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import algo.commons.Graph;
import algo.commons.Vertex;
import algo.sorting.Sort;

public class TopologicalSorting<T> implements Sort<T> {
	private final Set<Vertex<T>>	visitedNodes , allVertices;
	private final Queue<Vertex<T>>	outputStack;

	public TopologicalSorting(Graph<Vertex<T>> graph) {
		this.allVertices = new HashSet<>(graph.getVertices());
		this.visitedNodes = new HashSet<>();
		this.outputStack = asLifoQueue(new LinkedList<>());
	}

	@Override
	public List<T> sort() {
		for (Vertex<T> vertex : allVertices)
			if (!visitedNodes.contains(vertex))
				handleVertex(vertex);

		return outputStack.stream()
				.map(Vertex::getData)
				.collect(toList());
	}

	private void handleVertex(Vertex<T> sourceVertex) {
		visitedNodes.add(sourceVertex);

		for (Vertex<T> adjacentVertex : sourceVertex.adjacentVertices())
			if (!visitedNodes.contains(adjacentVertex))
				handleVertex(adjacentVertex);

		outputStack.offer(sourceVertex);
	}
}
