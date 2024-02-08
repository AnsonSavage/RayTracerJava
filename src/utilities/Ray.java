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

    public Ray sampleJitteredRay(double maxOffsetAngleDegrees) {
        double maxAngleRadians = Math.toRadians(maxOffsetAngleDegrees);

        double theta = Math.random() * maxAngleRadians;

        double phi = Math.random() * 2 * Math.PI;

        // Convert from polar to cartesian coordinates
        Vector3 jitteredDirection = new Vector3(
                Math.sin(theta) * Math.cos(phi),
                Math.sin(theta) * Math.sin(phi),
                Math.cos(theta)
        );

        // (Note that as of right now, the jittered ray is jittered off of the z axis. So now we need to rotate it to the direction of the original ray.)

        double angleOffsetFromZAxisRadians = Math.acos(this.direction.getZ()); // This is shorthand for Math.acos(this.direction.dot(new Vector3(0, 0, 1))). Note that this works because direction is normalized.
        Vector3 vectorOfRotation = (new Vector3(0, 0, 1)).cross(this.direction);
        vectorOfRotation.normalize();

        Vector3 finalJitteredDirection = jitteredDirection.rotateVectorAroundAxis(vectorOfRotation, angleOffsetFromZAxisRadians);

        return new Ray(origin, finalJitteredDirection);
    }
}
