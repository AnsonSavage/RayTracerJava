package utilities;

public class Color extends Vector3 {
    public Color() {
        super();
    }
    public Color(double r, double g, double b) {
        super(r, g, b);
    }
    public void setRGB(double r, double g, double b) {
        setX(r);
        setY(g);
        setZ(b);
    }

    public double getRDouble() {
        return getX();
    }

    public double getGDouble() {
        return getY();
    }

    public double getBDouble() {
        return getZ();
    }

    public int getRInt() {
        return (int) (getX() * 255);
    }

    public int getGInt() {
        return (int) (getY() * 255);
    }

    public int getBInt() {
        return (int) (getZ() * 255);
    }
}
