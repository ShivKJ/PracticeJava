package algo.commons.speceGeometry;

import static algo.commons.speceGeometry.Utils.isEqual;

public class XYZ extends XY {
	private final double z;

	public XYZ(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	@Override
	public String toString() {
		return "[" + x + " ," + y + " ," + z + "]";
	}

	@Override
	public double Z() {
		return z;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj)
				&& obj instanceof XYZ && isEqual(((XYZ) obj).z, z, TOLERANCE);
	}
}
