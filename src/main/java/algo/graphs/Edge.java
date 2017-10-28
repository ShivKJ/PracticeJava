package algo.graphs;

import static java.lang.Integer.compare;

public abstract class Edge<T extends Vertex<?>> implements Comparable<Edge<T>> {
	private final T src , dst;

	public Edge(T src, T dst) {
		this.src = src;
		this.dst = dst;
	}

	public abstract int distance();

	public T getSrc() {
		return src;
	}

	public T getDst() {
		return dst;
	}

	@Override
	public int compareTo(Edge<T> o) {
		return compare(distance(), o.distance());
	}

}
