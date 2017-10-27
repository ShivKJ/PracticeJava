package algo.graphs.traversal;

import static algo.graphs.traversal.StatusCode.DONE;
import static algo.graphs.traversal.StatusCode.NEW;
import static algo.graphs.traversal.StatusCode.QUEUED;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public final class Traversals {
	public Traversals() {}

	@SuppressWarnings("unchecked")
	public static final <T extends TraversalVertex<E>, E> List<T> bfs(T srcVrtx, Consumer<T> vertexProcessor) {
		// Only those vertices will be processed which have status code NEW.

		List<T> output = new ArrayList<>();

		if (isNew(srcVrtx)) {
			srcVrtx.setDepth(0);
			srcVrtx.setParent(null);

			Queue<T> queue = new BFSQueue<>(vertexProcessor);
			queue.add(srcVrtx);

			while (!queue.isEmpty()) {
				T curr = queue.poll();

				int childDepth = curr.depth() + 1;

				for (TraversalVertex<E> v : curr.adjacentVertices())
					if (isNew(v)) {
						v.setParent(curr);
						v.setDepth(childDepth);

						queue.add((T) v);
					}

				output.add(curr);
			}
		}

		return output;
	}

	public static final <T extends TraversalVertex<E>, E> List<T> bfs(T srcVrtx) {
		return bfs(srcVrtx, t -> {});
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
		return v.statusCode() == NEW;
	}
}
