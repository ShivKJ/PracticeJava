package algo.point2d;

import org.apache.commons.math3.ml.clustering.Clusterable;

public interface Point extends Clusterable {
	Point ZERO = new XY(0, 0) , E1 = new XY(1, 0) , E2 = new XY(0, 1);

	double X();

	double Y();

	default double magnitude() {
		return Utils.magnitude(this);
	}

	default Point unitVector() {
		return Utils.unitVector(this);
	}

	@Override
	default double[] getPoint() {

		return new double[] { X(), Y() };
	}
}
