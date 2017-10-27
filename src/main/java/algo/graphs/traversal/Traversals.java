package algo.graphs.traversal;

import static algo.graphs.traversal.StatusCode.DONE;
import static algo.graphs.traversal.StatusCode.NEW;
import static algo.graphs.traversal.StatusCode.QUEUED;
import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public final class Traversals {
	public Traversals() {}

	public static <T> List<TraversalVertex<T>> bfs(Collection<TraversalVertex<T>> vertexs, Consumer<TraversalVertex<T>> vertexProcessor) {
		if (vertexs.isEmpty())
			return emptyList();

		return bfs(vertexs.iterator().next(), vertexProcessor);
	}

	public static <T> List<TraversalVertex<T>> bfs(Collection<TraversalVertex<T>> vertices) {
		if (vertices.isEmpty())
			return emptyList();

		// all vertices must be in state NEW.
		return bfs(vertices.iterator().next(), t -> {});
	}

	private static final <T> List<TraversalVertex<T>> bfs(TraversalVertex<T> s, Consumer<TraversalVertex<T>> vertexProcessor) {

		s.setDepth(0);
		s.setParent(null);

		Queue<TraversalVertex<T>> queue = new BFSQueue<>(vertexProcessor);
		queue.add(s);

		List<TraversalVertex<T>> output = new ArrayList<>();

		while (!queue.isEmpty()) {
			TraversalVertex<T> curr = queue.poll();

			for (TraversalVertex<T> v : curr.adjacentVertices())
				if (isNew(v)) {
					v.setParent(curr);
					v.setDepth(curr.depth() + 1);

					queue.add(v);
				}

			output.add(curr);
		}

		return output;
	}

	private static final class BFSQueue<T extends TraversalVertex<?>> extends LinkedList<T> {

		private static final long	serialVersionUID	= 1L;
		private final Consumer<T>	vertexProcessor;

		public BFSQueue(Consumer<T> vertexProcessor) {
			this.vertexProcessor = vertexProcessor;
		}

		@Override
		public boolean add(T e) {
			e.setStatusCode(QUEUED);
			return super.add(e);
		}

		@Override
		public T poll() {
			T polled = super.poll();
			vertexProcessor.accept(polled);

			polled.setStatusCode(DONE);

			return polled;
		}
	}

	private static <T> boolean isNew(TraversalVertex<T> v) {
		return v.getStatusCode() == NEW;
	}
}
