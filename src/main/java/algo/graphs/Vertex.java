package algo.graphs;

public interface Vertex {

	@Override
	int hashCode();

	@Override
	boolean equals(Object obj);

	@Override
	String toString();

	int uid();

	default <T> T userData() {
		return null;
	}

	default <T> void setUserData(T data) {
		throw new UnsupportedOperationException();
	}

}
