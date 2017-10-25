package algo.spaceGeometry.convexhull;

public class NotConvexHullException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotConvexHullException(String message) {
		super(message);
	}

	public NotConvexHullException() {
		super("Not a convex Hull.");
	}
}
