package algo.spaceGeometry;

public interface Point {
	Point ZERO = new XY(0, 0) , E1 = new XY(1, 0) , E2 = new XY(0, 1);

	double X();

	double Y();

	default double magnitude() {
		return PointUtils.magnitude(this);
	}

	default Point unitVector() {
		return PointUtils.unitVector(this);
	}
}
