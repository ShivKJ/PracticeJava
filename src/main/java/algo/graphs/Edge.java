package algo.graphs;

import static java.lang.Double.compare;

public abstract class Edge<T extends Vertex> implements Comparable<Edge<T>> {
	private final T src , dst;

	public Edge(T src, T dst) {
		this.src = src;
		this.dst = dst;
	}

	public abstract double distance();

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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge<?>) {
			Edge<?> edge = (Edge<?>) obj;
			return src.uid() == edge.src.uid() && dst.uid() == edge.dst.uid();
		}

		return false;
	}

}
