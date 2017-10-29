package algo.graphs.traversal;

import java.util.Collection;
import java.util.LinkedList;

public final class Parent<T> extends TraversalVertex<T> {
	private Collection<TraversalVertex<T>> pointsInCollection;

	public Parent(TraversalVertex<T> v) {
		this.pointsInCollection = new LinkedList<>();
		add(v);
	}

	public void setData(Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Parent<T> parent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setParent(TraversalVertex<T> parent) {
		throw new UnsupportedOperationException();
	}

	public Collection<TraversalVertex<T>> pointInSet() {
		return pointsInCollection;
	}

	public void union(Collection<? extends TraversalVertex<T>> points) {
		points.forEach(this::add);
	}

	private void add(TraversalVertex<T> v) {
		pointsInCollection.add(v);
		v.setParent(this);

	}

	@Override
	public T getData() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object obj) {

		return this == obj;
	}
}
