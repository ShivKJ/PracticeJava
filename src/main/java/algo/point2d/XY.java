package algo.point2d;

public class XY extends AbstractPoint {

    private final double x, y;

    public XY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double X() {
        return x;
    }

    @Override
    public double Y() {
        return y;
    }

    @Override
    public double[] getPoint() {
        return new double[] { x, y };
    }
}
