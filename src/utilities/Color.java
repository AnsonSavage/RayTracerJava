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
    public void clamp() {
        if (getX() > 1) {
            setX(1);
        }
        if (getY() > 1) {
            setY(1);
        }
        if (getZ() > 1) {
            setZ(1);
        }
    }

    public boolean colorIsValid() {
        return getX() >= 0 && getY() >= 0 && getZ() >= 0;
    }
}
