package algo.spaceGeometry.clustering.leaderCluster;

import static java.lang.Double.doubleToLongBits;
import static java.nio.file.Paths.get;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import algo.io.IO;
import algo.io.PointGeneration;
import algo.spaceGeometry.Config;
import algo.spaceGeometry.Point;
import algo.spaceGeometry.Utils;
import algo.spaceGeometry.XY;
import algo.spaceGeometry.clustering.WeightedPoint;
import algo.spaceGeometry.convexhull.ConvexHulls;

public class TestingLeaderCluster {
	static Random random = new Random(10L);

	public static void main(String[] args) throws IOException {
		Collection<Point> points = new LinkedList<>();

		PointGeneration.setSeed(10L);
		points.addAll(PointGeneration.pointsOnCircle(new XY(0, 0), 0, 0.6, 100));
		points.addAll(PointGeneration.pointsOnCircle(new XY(1.6, 0), 0, 0.6, 100));
		points.addAll(PointGeneration.pointsOnCircle(new XY(0, 1), 0, 0.6, 100));
		points.addAll(PointGeneration.pointsOnCircle(new XY(1, 1), 0, 0.6, 100));
		IO.toJson(points, Config.PATH + "p.json");

		LeaderCluster<WPoint> leaderCluster = LeaderCluster.Builder.<WPoint>newInstance()
				.setDistanceMeasure(new EuclideanDistance())
				.setMaxClusterSize(80)
				.setMaxRadius(1)
				.setMaxWeight(40)
				.build();

		Collection<WPoint> collection = points.stream()
				.map(p -> new WPoint(p, random.nextDouble()))
				.collect(toList());

		List<Cluster<WPoint>> clusters = leaderCluster.cluster(collection);

		for (Cluster<WPoint> cluster : clusters)
			System.out.println(cluster.getPoints().size());
		int i = 1;
		String dirPath = Config.PATH + "convexfiles/";

		Files.find(get(dirPath), 1, (a, t) -> !Files.isDirectory(a)).forEach(t -> {
			try {
				Files.deleteIfExists(t);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		for (Cluster<WPoint> cluster : clusters)
			IO.toJson(ConvexHulls.cHull(cluster.getPoints()), dirPath + i++ + ".json");
	}

	static class WPoint implements WeightedPoint {
		transient final double	xy[] , w;
		double					x , y;

		public WPoint(Point point, double weight) {
			this.w = weight;
			this.xy = point.getPoint();
			this.x = point.X();
			this.y = point.Y();
		}

		@Override
		public double X() {

			return xy[0];
		}

		@Override
		public double Y() {

			return xy[1];
		}

		@Override
		public double weight() {

			return w;
		}

		@Override
		public double[] getPoint() {

			return xy;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof Point && Utils.isEqual(((Point) obj).X(), X()) && Utils.isEqual(((Point) obj).Y(), Y());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;

			long temp = doubleToLongBits(X());
			result = prime * result + (int) (temp ^ temp >>> 32);
			temp = doubleToLongBits(Y());
			return prime * result + (int) (temp ^ temp >>> 32);
		}

		@Override
		public String toString() {
			return Arrays.toString(xy);
		}

	}
}
