package algo.spaceGeometry;

import static algo.spaceGeometry.PointUtils.line;

import org.apache.commons.math3.ml.clustering.Clusterable;

public interface Point extends Clusterable {
	Point ZERO = new XY(0, 0) , E1 = new XY(1, 0) , E2 = new XY(0, 1);

	double X();

	double Y();

	default double magnitude() {
		return PointUtils.magnitude(this);
	}

	default Point unitVector() {
		return PointUtils.unitVector(this);
	}

	default Object getData() {
		throw new UnsupportedOperationException();
	}

	default void setData(Object data) {
		throw new UnsupportedOperationException();
	}

	default Point to(Point to) {
		return line(this, to);
	}

	@Override
	default double[] getPoint() {
		return new double[] { X(), Y() };
	}

}
