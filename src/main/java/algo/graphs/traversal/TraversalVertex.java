package algo.graphs.traversal;

import algo.graphs.Vertex;

public abstract class TraversalVertex implements Vertex {
	private TraversalVertex		parent;
	private VertexTraversalCode	code;

	public TraversalVertex() {
		this.parent = null;
	}

	public void setParent(TraversalVertex parent) {
		this.parent = parent;
	}

	public TraversalVertex parent() {
		return parent;
	}

	public VertexTraversalCode code() {
		return code;
	}

	public void setCode(VertexTraversalCode statusCode) {
		this.code = statusCode;
	}

	@Override
	public int hashCode() {
		return uid();
	}

}
