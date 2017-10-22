package algo.spaceGeometry.convexhull;

import static algo.spaceGeometry.PointLocation.INSIDE;
import static algo.spaceGeometry.PointLocation.ON;
import static algo.spaceGeometry.PointLocation.OUTSIDE;
import static algo.spaceGeometry.Utils.crossProductZDirection;
import static algo.spaceGeometry.Utils.isZero;
import static algo.spaceGeometry.Utils.pointLocWRTLineSegment;
import static algo.spaceGeometry.ZDirection.UNDEFINED;

import algo.spaceGeometry.PointLocation;
import algo.spaceGeometry.Triangle;
import algo.spaceGeometry.XY;
import algo.spaceGeometry.ZDirection;

public class LocationFindingTriangle extends Triangle {
	private Boolean isAreaZero;

	public LocationFindingTriangle(XY a, XY b, XY c) {

		super(a, b, c);
	}

	private Boolean isAreaZero() {
		if (isAreaZero == null)
			isAreaZero = isZero(area()) ? true : false;

		return isAreaZero;
	}

	public PointLocation getPointLocation(XY p) {
		XY pa = p.to(a) , pb = p.to(b) , pc = p.to(c);

		if (isAreaZero())
			return pointLocWRTLineSegment(a.to(b), pa, pb) == ON || pointLocWRTLineSegment(b.to(c), pb, pc) == ON
					? ON
					: OUTSIDE;

		ZDirection ab = crossProductZDirection(pa, pb);
		if (ab == UNDEFINED)
			return pointLocWRTLineSegment(a.to(b), pa, pb);

		ZDirection bc = crossProductZDirection(pb, pc);
		if (bc == UNDEFINED)
			return pointLocWRTLineSegment(b.to(c), pb, pc);

		ZDirection ca = crossProductZDirection(pc, pa);
		if (ca == UNDEFINED)
			return pointLocWRTLineSegment(c.to(a), pc, pa);

		return ab == bc && bc == ca ? INSIDE : OUTSIDE;
	}

}
