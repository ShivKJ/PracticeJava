package algo.commons.spaceGeometry;

import static algo.commons.spaceGeometry.Utils.isEqual;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp = Double.doubleToLongBits(z);
		return prime * result + (int) (temp ^ temp >>> 32);
	}

}
