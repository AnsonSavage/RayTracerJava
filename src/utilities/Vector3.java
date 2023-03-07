package utilities;

public class Vector3 {
    private double x;
    private double y;
    private double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
        this(0, 0, 0);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public void add(Vector3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }

    public Vector3 addNew(Vector3 v) {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public void subtract(Vector3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
    }

    public Vector3 subtractNew(Vector3 v) {
        return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public Vector3 multiply(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    public Vector3 multiplyNew(double scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public void divide(double scalar) {
        this.x /= scalar;
        this.y /= scalar;
        this.z /= scalar;
    }

    public Vector3 divideNew(double scalar) {
        return new Vector3(this.x / scalar, this.y / scalar, this.z / scalar);
    }

    public double dot(Vector3 v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    public Vector3 componentWiseMultiply(Vector3 v) {
        return new Vector3(this.x * v.x, this.y * v.y, this.z * v.z);
    }

    /**
     * Computes the reflection of this vector across a given normal
     * @param normal
     * @return The reflection of this vector across a given normal
     */
    public Vector3 reflect(Vector3 normal) {
        double dot = this.dot(normal);
        double distanceFromSurface = 2 * dot;
        Vector3 vectorDistanceFromSurface = normal.multiplyNew(distanceFromSurface);
        vectorDistanceFromSurface.subtract(this); // This is now the reflected vector
        return vectorDistanceFromSurface;
    }

    public Vector3 cross(Vector3 v) {
        return new Vector3(
                this.y * v.z - this.z * v.y,
                this.z * v.x - this.x * v.z, // the negative sign is shown just by subtracting in a different order
                this.x * v.y - this.y * v.x
        );
    }

    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public void normalize() {
        double magnitude = this.magnitude();
        if (magnitude == 0) {
            return;
        }
        this.x /= magnitude;
        this.y /= magnitude;
        this.z /= magnitude;
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public boolean isNormalized() {
        double epsilon = 0.00001;
        return Math.abs(this.magnitude() - 1) < epsilon;
    }

    public Color convertToColor() {
        return new Color(this.x, this.y, this.z);
    }
}
