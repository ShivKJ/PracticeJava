package algo.spaceGeometry;

public class XYHash extends XY {
	private final int	hashcode;

	public XYHash(double x, double y) {
		super(x, y);
		this.hashcode = super.hashCode();
	}

	public XYHash(XY point) {
		this(point.x, point.y);
	}

	@Override
	public int hashCode() {
		return hashcode;
	}

}
