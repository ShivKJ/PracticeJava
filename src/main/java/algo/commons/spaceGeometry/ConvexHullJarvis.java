package algo.commons.spaceGeometry;

import static java.util.Collections.addAll;
import static java.util.Collections.min;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class ConvexHullJarvis {

	private List<XY> input;

	public ConvexHullJarvis(Collection<XY> points) {
		this.input = new ArrayList<>(points);
	}

	public List<XY> getHull() {
		if (input.size() <= 2)
			return input;

		List<XY> convexHull = new LinkedList<>();

		XY[] firstTwoPoints = getFirstTwoPoints();
		addAll(convexHull, firstTwoPoints);

		XY curr = firstTwoPoints[1];
		XY line = firstTwoPoints[0].getLine(curr);

		while (curr != firstTwoPoints[0]) {
			XY tmp = input.stream().filter(getP(curr)).max(getComparator(curr, line)).get();
			convexHull.add(tmp);
			line = curr.getLine(tmp);
			curr = tmp;
		}

		return convexHull;
	}

	private static Comparator<XY> getComparator(XY curr, XY line) {
		double mag = line.getMagnitude();

		return comparingDouble(x -> {
			XY tmp = curr.getLine(x);
			return tmp.dotProduct(line) / tmp.getMagnitude() / mag;
		});
	}

	private Predicate<XY> getP(XY curr) {
		return x -> x != curr;
	}

	private XY[] getFirstTwoPoints() {

		XY firstPoint = min(input, comparing(Coordinate::X));

		XY nextPoint = input.stream()
				.filter(x -> x != firstPoint)
				.max(comparingDouble(p -> {
					XY newLine = firstPoint.getLine(p);
					return newLine.y / newLine.getMagnitude();
				}))
				.get();

		return new XY[] { firstPoint, nextPoint };
	}

}
