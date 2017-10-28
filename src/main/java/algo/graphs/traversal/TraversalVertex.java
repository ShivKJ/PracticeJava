package algo.graphs.traversal;

import algo.graphs.Vertex;

public abstract class TraversalVertex<T> implements Vertex<T> {
	private final T				data;
	private TraversalVertex<T>	parent;
	private VertexTraversalCode	statusCode;

	public TraversalVertex(T data) {
		this.data = data;
		this.parent = null;
	}

	public void setParent(TraversalVertex<T> parent) {
		this.parent = parent;
	}

	public TraversalVertex<T> parent() {
		return parent;
	}

	@Override
	public T getData() {

		return data;
	}

	public VertexTraversalCode statusCode() {
		return statusCode;
	}

	public void setStatusCode(VertexTraversalCode statusCode) {
		this.statusCode = statusCode;
	}

}
