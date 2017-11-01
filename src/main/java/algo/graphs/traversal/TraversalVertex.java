package algo.graphs.traversal;

import algo.graphs.Vertex;

public abstract class TraversalVertex<T> implements Vertex<T> {
	private TraversalVertex<T>	parent;
	private VertexTraversalCode	code;
	private Object				vData;

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

	@SuppressWarnings("unchecked")
	public <E> E getvData() {
		return (E) vData;
	}

	public <E> void setvData(E vData) {
		this.vData = vData;
	}

	public abstract <E> void updateVData(E e);

}
