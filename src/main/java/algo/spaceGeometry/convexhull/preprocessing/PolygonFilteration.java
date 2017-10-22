package algo.spaceGeometry.convexhull.preprocessing;

import static algo.spaceGeometry.Utils.getFarthestPoint;
import static java.util.Collections.emptyList;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import algo.spaceGeometry.XY;
import algo.spaceGeometry.convexhull.ConvexHullJarvisOptimised;
import algo.spaceGeometry.convexhull.EmptyCollectionException;

public class PolygonFilteration {
	private static final int NO_THREADS = 1;

	private final double[]			angles;
	private final Collection<XY>	points;
	private final ExecutorService	ftp;

	public PolygonFilteration(Collection<XY> points, double[] angles, int threads) throws EmptyCollectionException {
		if (points.isEmpty())
			throw new EmptyCollectionException("input can not be empty.");
		this.angles = angles;
		this.points = points;
		this.ftp = newFixedThreadPool(threads);
	}

	public PolygonFilteration(Collection<XY> points, double[] angles) throws EmptyCollectionException {
		this(points, angles, NO_THREADS);
	}

	public List<XY> run() {
		List<Future<XY>> hullPoints = new ArrayList<>(angles.length);

		for (double d : angles)
			hullPoints.add(ftp.submit(() -> getFarthestPoint(points, d).get()));

		ftp.shutdown();

		try {
			ftp.awaitTermination(Long.MAX_VALUE, SECONDS);
		} catch (InterruptedException e) {
			System.out.println("Terminating process.");
		}

		try {
			return new ConvexHullJarvisOptimised(hullPoints.stream().map((Future<XY> t) -> {
				try {
					return t.get();
				} catch (InterruptedException | ExecutionException e) {
					System.out.println("Task interrupted. Adding Null");
				}
				return null;
			}).filter(x -> x != null).collect(toList())).getConvexHull();
		} catch (EmptyCollectionException e) {
			e.printStackTrace();
		}
		return emptyList();// will never reach here.
	}

}
