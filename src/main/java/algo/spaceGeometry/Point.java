package algo.spaceGeometry;

import static java.lang.Math.hypot;

import org.apache.commons.math3.ml.clustering.Clusterable;

public interface Point extends Clusterable {
	Point ZERO = new XY(0, 0) , E1 = new XY(1, 0) , E2 = new XY(0, 1);

	double X();

	double Y();

	

	default double magnitude() {
		return hypot(X(), Y());
	}

	default double distanceTo(Point to) {
		return hypot(X() - to.X(), Y() - to.Y());
	}

	default Object getData() {
		throw new UnsupportedOperationException();
	}

	default void setData() {
		throw new UnsupportedOperationException();
	}

	@Override
	default double[] getPoint() {
		return new double[] { X(), Y() };
	}

}
