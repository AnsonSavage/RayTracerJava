package utilities;

public class Ray {
    private Vector3 origin;
    private Vector3 direction;
    private double originOffset = 0.00000001;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vector3 getRayEnd(double t) {
        return origin.addNew(direction.multiplyNew(t));
    }

    public Vector3 getOrigin() {
        return origin;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public String toString() {
        return "Ray: " + origin + " -> " + direction;
    }

    public boolean isNormalized() {
        return direction.isNormalized();
    }

    public void offsetFromOrigin() {
        origin = origin.addNew(direction.multiplyNew(originOffset));
    }

    public void offsetFromOrigin(Vector3 direction) {
        origin = origin.addNew(direction.multiplyNew(originOffset));
    }
}
