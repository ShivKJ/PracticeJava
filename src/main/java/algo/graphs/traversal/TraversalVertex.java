package algo.graphs.traversal;

import static algo.graphs.traversal.StatusCode.NEW;

import java.util.Set;

import algo.graphs.Vertex;

public abstract class TraversalVertex<T> implements Vertex<T> {
	private TraversalVertex<T>	parent;
	private StatusCode			statusCode;

	public TraversalVertex() {
		this.parent = null;
		this.statusCode = NEW;
	}

	@SuppressWarnings("unchecked")
	@Override
	public abstract Set<TraversalVertex<T>> adjacentVertices();

	public void setParent(TraversalVertex<T> parent) {
		this.parent = parent;
	}

	public TraversalVertex<T> parent() {
		return parent;
	}

	public StatusCode statusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}
}
