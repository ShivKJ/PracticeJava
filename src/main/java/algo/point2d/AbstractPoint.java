package algo.point2d;

import static java.util.Objects.hash;

public abstract class AbstractPoint implements Point {
	@Override
	public String toString() {
		return "[" + X() + ", " + Y() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Point
		    && Utils.isEqual(((Point) obj).X(), X())
		    && Utils.isEqual(((Point) obj).Y(), Y());
	}

	@Override
	public int hashCode() {
		return hash(X(), Y());
	}

}
