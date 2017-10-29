package algo.graphs.traversal;

import algo.graphs.Vertex;

public abstract class TraversalVertex<T> implements Vertex<T> {
	private TraversalVertex<T>	parent;
	private VertexTraversalCode	statusCode;

	public TraversalVertex() {
		this.parent = null;
	}

	@Override
	public abstract T getData();

	public void setParent(TraversalVertex<T> parent) {
		this.parent = parent;
	}

	public TraversalVertex<T> parent() {
		return parent;
	}

	public VertexTraversalCode statusCode() {
		return statusCode;
	}

	public void setStatusCode(VertexTraversalCode statusCode) {
		this.statusCode = statusCode;
	}

}
