package utilities;

import java.util.ArrayList;
import java.util.List;

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

    public void jitterRayOriginAndDirection(double maxJitterAmount, double distanceToPoint) {
        // Jitter the origin within a sphere of radius maxJitterAmount
        double offsetX = (Math.random() * 2 - 1) * maxJitterAmount;
        double offsetY = (Math.random() * 2 - 1) * maxJitterAmount;
        double offsetZ = (Math.random() * 2 - 1) * maxJitterAmount;
        Vector3 jitterOrigin = new Vector3(offsetX, offsetY, offsetZ);

        if (jitterOrigin.magnitude() > maxJitterAmount) {
            jitterOrigin.normalize(); // Normalize the jitter direction
            jitterOrigin = jitterOrigin.multiplyNew(maxJitterAmount); // Scale to the max jitter amount
        }

        // Apply the jitter to the ray's origin
        Vector3 newOrigin = this.origin.addNew(jitterOrigin);

        // Determine the constraint point the jittered ray must pass through
        Vector3 constraintPoint = this.getRayEnd(distanceToPoint);

        // Adjust the direction of the ray to ensure it goes through the constraint point
        Vector3 newDirection = constraintPoint.subtractNew(newOrigin);
        double newDirectionMagnitude = newDirection.magnitude();
        newDirection.normalize(); // The direction should be normalized

        // Update the ray's properties
        this.origin = newOrigin;
        this.direction = newDirection;
    }


    public Ray sampleJitteredRay(double maxOffsetAngleDegrees) {
        return getNJitteredRays(maxOffsetAngleDegrees, 1).get(0);
    }

    public List<Ray> getNJitteredRays(double maxOffsetAngleDegrees, int n) {
        double maxAngleRadians = Math.toRadians(maxOffsetAngleDegrees);
        double angleOffsetFromZAxisRadians = Math.acos(this.direction.getZ()); // This is shorthand for Math.acos(this.direction.dot(new Vector3(0, 0, 1))). Note that this works because direction is normalized.
        Vector3 vectorOfRotation = (new Vector3(0, 0, 1)).cross(this.direction);
        vectorOfRotation.normalize();

        List<Ray> jitteredRays = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            double theta = Math.sqrt(Math.random()) * maxAngleRadians;
            double phi = Math.random() * 2 * Math.PI;

            // Convert from polar to cartesian coordinates
            Vector3 jitteredDirection = new Vector3(
                    Math.sin(theta) * Math.cos(phi),
                    Math.sin(theta) * Math.sin(phi),
                    Math.cos(theta)
            );

            // (Note that as of right now, the jittered ray is jittered off of the z axis. So now we need to rotate it to the direction of the original ray.)
            Vector3 finalJitteredDirection = jitteredDirection.rotateVectorAroundAxis(vectorOfRotation, angleOffsetFromZAxisRadians);
            jitteredRays.add(new Ray(origin, finalJitteredDirection));
        }
        return jitteredRays;
    }

}
