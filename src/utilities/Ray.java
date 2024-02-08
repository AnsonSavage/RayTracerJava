package utilities;

public class Ray {
    private Vector3 origin;
    private Vector3 direction;
    private double originOffset = 0.00000001;


    private double originalLength;

    public Ray(Vector3 origin, Vector3 direction) {
        this.origin = origin;
        this.direction = direction.copy();
        this.originalLength = this.direction.magnitude();
        this.direction.normalize();
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
        return "Ray: " + origin + " -> " + direction + " (original length: " + originalLength + ")";
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
    public double getOriginalLength() {
        return originalLength;
    }

    public Ray sampleRayFromCone(double maxAngleDegrees) {
        double maxAngleRadians = Math.toRadians(maxAngleDegrees);

        double theta = Math.random() * maxAngleRadians;

        double phi = Math.random() * 2 * Math.PI;

        // Assume we're jittering off the Z axis for now:
        Vector3 jitteredDirection = new Vector3(
                Math.sin(theta) * Math.cos(phi),
                Math.sin(theta) * Math.sin(phi),
                Math.cos(theta)
        );

        return new Ray(origin, jitteredDirection);
    }
}
