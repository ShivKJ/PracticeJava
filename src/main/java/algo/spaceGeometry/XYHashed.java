package algo.spaceGeometry;

public class XYHashed extends XY {
	private transient final int hashcode;

	public XYHashed(double x, double y) {
		super(x, y);
		this.hashcode = super.hashCode();
	}

	public XYHashed(XY point) {
		this(point.x, point.y);
	}

	@Override
	public int hashCode() {
		return hashcode;
	}

}
