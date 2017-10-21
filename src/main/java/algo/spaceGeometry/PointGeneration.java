package algo.spaceGeometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class PointGeneration {
	private final static Random random = new Random(10L);

	private PointGeneration() {}

	public static final Collection<XY> pointsOnCircle(double r1, double r2, double theta1, double theta2, int noPoints) {
		Collection<XY> points = new ArrayList<>(noPoints);

		if (theta2 < theta1)
			theta2 += 360;

		for (int i = 0; i < noPoints; i++) {
			double rnd1 = random.nextDouble() , rnd2 = random.nextDouble();
			points.add(XY.rTheta(r1 + rnd1 * (r2 - r1), theta1 + rnd2 * (theta2 - theta1)));
		}

		return points;
	}

	public static final Collection<XY> pointsOnCircle(double r1, double r2, int noPoints) {
		return pointsOnCircle(r1, r2, 0, 360, noPoints);
	}

	public static final Collection<XY> pointsOnLine(XY a, XY b, double width, int noPoints) {
		Collection<XY> points = new ArrayList<>(noPoints);
		XY ab = a.to(b);
		XY perpendicular = Utils.rotate(ab, 90).unitVector();

		for (int i = 0; i < noPoints; i++) {
			double t1 = random.nextDouble() , t2 = random.nextDouble() - 0.5;
			double x = a.X() + t1 * (b.X() - a.X()) , y = a.Y() + t1 * (b.Y() - a.Y());

			x += width * t2 * perpendicular.X();
			y += width * t2 * perpendicular.Y();

			points.add(new XY(x, y));
		}

		return points;
	}

	public static final Collection<XY> pointsOnTriangle(XY a, XY b, XY c, double width, int noPoints) {
		return pointsOnPolygon(Arrays.asList(a, b, c), width, noPoints);
	}

	public static final Collection<XY> pointsOnRectangle(XY a, XY b, XY c, XY d, double width, int noPoints) {
		return pointsOnPolygon(Arrays.asList(a, b, c, d), width, noPoints);
	}

	public static final Collection<XY> pointsOnPolygon(List<XY> polygon, double width, int noPoints) {
		int n = polygon.size() , div = noPoints / n , mod = noPoints % n;
		Iterator<XY> iterator = polygon.iterator();
		XY a = iterator.next();

		List<XY> points = new ArrayList<>(noPoints);

		while (iterator.hasNext())
			points.addAll(pointsOnLine(a, a = iterator.next(), width, mod-- > 0 ? div + 1 : div));

		points.addAll(pointsOnLine(a, polygon.get(0), width, div));

		return points;
	}

	public static final Collection<XY> pointsOnClosePolygon(List<XY> polygon, double width, int noPoints) {
		return pointsOnPolygon(polygon.subList(0, polygon.size() - 2), width, noPoints);

	}

}
