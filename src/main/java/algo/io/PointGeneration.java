package algo.io;

import static algo.spaceGeometry.Utils.rotate;
import static algo.spaceGeometry.XY.ZERO;
import static algo.spaceGeometry.XY.rTheta;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparingDouble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import algo.spaceGeometry.XY;

public final class PointGeneration {
	private final static Random random = new Random();

	private PointGeneration() {}

	public static void setSeed(long seed) {
		random.setSeed(seed);
	}

	public static final Collection<XY> pointsOnCircle(XY center, double r1, double r2, double theta1, double theta2, int noPoints) {
		Collection<XY> points = new ArrayList<>(noPoints);

		if (theta2 < theta1)
			theta2 += 360;

		for (int i = 0; i < noPoints; i++) {
			double rnd1 = random.nextDouble() , rnd2 = random.nextDouble();
			points.add(rTheta(center, r1 + rnd1 * (r2 - r1), theta1 + rnd2 * (theta2 - theta1)));
		}

		return points;
	}

	public static final Collection<XY> pointsOnCircle(double r1, double r2, double theta1, double theta2, int noPoints) {
		return pointsOnCircle(ZERO, r1, r2, theta1, theta2, noPoints);
	}

	public static final Collection<XY> pointsOnCircle(double r1, double r2, int noPoints) {
		return pointsOnCircle(r1, r2, 0, 360, noPoints);
	}

	public static final Collection<XY> pointsOnCircle(XY center, double r1, double r2, int noPoints) {
		return pointsOnCircle(center, r1, r2, 0, 360, noPoints);
	}

	public static final Collection<XY> pointsOnLine(XY a, XY b, double width, int noPoints) {
		Collection<XY> points = new ArrayList<>(noPoints);
		XY ab = a.to(b);
		XY perpendicular = rotate(ab, 90).unitVector();

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
		return pointsOnPolygon(asList(a, b, c, a), width, noPoints);
	}

	public static final Collection<XY> pointsOnRectangle(XY a, XY b, XY c, XY d, double width, int noPoints) {
		return pointsOnPolygon(asList(a, b, c, d, a), width, noPoints);
	}

	private static class LineSegement {
		final XY		a , b;
		final double	mag;

		LineSegement(XY a, XY b) {
			this.a = a;
			this.b = b;
			this.mag = a.distanceTo(b);
		}

		private double getMag() {
			return mag;
		}

		static List<LineSegement> getLineSegs(List<XY> polys) {
			List<LineSegement> lineSegements = new ArrayList<>(polys.size() - 1);
			Iterator<XY> iter = polys.iterator();

			XY prev = iter.next();

			while (iter.hasNext())
				lineSegements.add(new LineSegement(prev, prev = iter.next()));

			return lineSegements;
		}

	}

	public static final Collection<XY> pointsOnPolygon(List<XY> polygon, double width, int noPoints) {
		if (polygon.isEmpty())
			return emptyList();

		if (polygon.size() == 1)
			return asList(polygon.get(0));

		List<LineSegement> lineSegements = LineSegement.getLineSegs(polygon);

		lineSegements.sort(comparingDouble(LineSegement::getMag).reversed());

		double length = lineSegements.stream().mapToDouble(LineSegement::getMag).sum();

		List<XY> points = new ArrayList<>(noPoints);

		int[] indiPoints = lineSegements.stream()
				.mapToInt(lineSegement -> (int) (lineSegement.mag * noPoints / length))
				.toArray();

		for (int distributedPoints = stream(indiPoints).sum(), i = 0; i < indiPoints.length; i++) {
			int addPoints = indiPoints[i];

			if (distributedPoints++ < noPoints)
				addPoints++;

			LineSegement lineSegement = lineSegements.get(i);
			points.addAll(pointsOnLine(lineSegement.a, lineSegement.b, width, addPoints));
		}
		return points;
	}

}
