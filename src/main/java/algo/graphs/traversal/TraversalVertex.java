package algo.graphs.traversal;

import static algo.graphs.traversal.StatusCode.NEW;

import java.util.Set;

import algo.graphs.Vertex;

public abstract class TraversalVertex<T> implements Vertex<T> {
	private int					depth;
	private TraversalVertex<T>	parent;
	private StatusCode			statusCode;

	public TraversalVertex() {
		this.statusCode = NEW;
		this.depth = 0;
		this.parent = null;
	}

	public int depth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public TraversalVertex<T> parent() {
		return parent;
	}

	void setParent(TraversalVertex<T> parent) {
		this.parent = parent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public abstract Set<TraversalVertex<T>> adjacentVertices();

	public StatusCode statusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

}
