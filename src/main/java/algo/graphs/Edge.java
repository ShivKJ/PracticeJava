package algo.graphs;

public abstract class Edge<T extends Vertex<?>> {
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

}
