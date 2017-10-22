package algo.spaceGeometry;

import static algo.spaceGeometry.Utils.crossProduct;
import static java.lang.Double.NaN;
import static java.lang.Double.isNaN;
import static java.lang.Math.abs;

public class Triangle {
	protected final XY	a , b , c;
	private double		area;

	public Triangle(XY a, XY b, XY c) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.area = NaN;
	}

	public double area() {
		if (isNaN(area))
			area = abs(crossProduct(a.to(b), a.to(c)));
		return area;
	}

}
