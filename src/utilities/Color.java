package utilities;

public class Color {
    private Vector3 rgbColor;
    public Color() {
        super();
    }
    public Color(double r, double g, double b) {
        rgbColor = new Vector3(r, g, b);
    }

    private Color (Vector3 rgbColor) {
        this.rgbColor = rgbColor;
    }

    public void setRGB(double r, double g, double b) {
        rgbColor.set(r, g, b);
    }

    public void setR(double r) {
        rgbColor.setX(r);
    }

    public void setG(double g) {
        rgbColor.setY(g);
    }

    public void setB(double b) {
        rgbColor.setZ(b);
    }

    public double getRDouble() {
        return rgbColor.getX();
    }

    public double getGDouble() {
        return rgbColor.getY();
    }

    public double getBDouble() {
        return rgbColor.getZ();
    }

    public int getRInt() {
        return (int) (getRDouble() * 255);
    }

    public int getGInt() {
        return (int) (getGDouble() * 255);
    }

    public int getBInt() {
        return (int) (getBDouble() * 255);
    }
    public void clamp() {
        // Clamp to 1
        if (getRDouble() > 1) {
            setR(1);
        }
        if (getGDouble() > 1) {
            setG(1);
        }
        if (getBDouble() > 1) {
            setB(1);
        }

        // Clamp to 0
        if (getRDouble() < 0) {
            setR(0);
        }
        if (getGDouble() < 0) {
            setG(0);
        }
        if (getBDouble() < 0) {
            setB(0);
        }
    }

    public boolean colorIsValid() {
        // True if all values are between 0 and 1
        return (getRDouble() >= 0 && getRDouble() <= 1) &&
                (getGDouble() >= 0 && getGDouble() <= 1) &&
                (getBDouble() >= 0 && getBDouble() <= 1);
    }

    public Color multiplyNew(double scalar) {
        return new Color(rgbColor.multiplyNew(scalar));
    }

    public void add(Color c) {
        rgbColor.add(c.rgbColor);
    }


    public Color addNew(Color c) {
        return new Color(rgbColor.addNew(c.rgbColor));
    }

    public Color subtractNew(Color c) {
        return new Color(rgbColor.subtractNew(c.rgbColor));
    }

    public Color componentWiseMultiply(Color c) {
        return new Color(rgbColor.componentWiseMultiplyNew(c.rgbColor));
    }

    public String toString() {
        return "Color(" + getRDouble() + ", " + getGDouble() + ", " + getBDouble() + ")";
    }
}
