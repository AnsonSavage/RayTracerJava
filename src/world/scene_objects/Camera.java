package world.scene_objects;

import utilities.Vector3;

public class Camera extends WorldObject {
    private double fieldOfViewX;
    private double fieldOfViewY;
    private double focalLength;
    private Vector3 up;

    private Vector3 lookAt;

    public Camera(Vector3 position, Vector3 lookAt, Vector3 up, double fieldOfViewX, double aspectRatio, double focalLength) {
        super(position);

        this.lookAt = lookAt.subtractNew(position); // The lookat vector is the vector from the camera position to the lookat point
        this.lookAt.normalize();

        this.focalLength = focalLength;
        this.fieldOfViewX = fieldOfViewX * Math.PI / 180;
        this.up = up;
        this.fieldOfViewY = computeFieldOfViewY(aspectRatio);
        // Note that both fieldOfViewX and fieldOfView Y are in radians
    }


    public Vector3 getLookAtVector() {
        return this.lookAt;
    }

    public Vector3 getImagePlaneCenter() {
        Vector3 lookAtVector = this.getLookAtVector();
        return this.position.addNew(lookAtVector.multiplyNew(this.focalLength));
    }

    public double getImagePlaneLengthX() {
        return 2 * this.focalLength * Math.tan(this.fieldOfViewX / 2);
    }

    public double getImagePlaneLengthY() {
        return 2 * this.focalLength * Math.tan(this.fieldOfViewY / 2);
    }

    public double getFieldOfViewX() {
        return fieldOfViewX;
    }

    public double getFieldOfViewY() {
        return fieldOfViewY;
    }

    public double getFocalLength() {
        return focalLength;
    }

    public Vector3 getUp() {
        return up;
    }

    private double computeFieldOfViewY(double aspectRatio) {
        double x = focalLength * Math.tan(fieldOfViewX / 2);
        // normalize x with respect to focal length
        x /= focalLength;
        // Multiply x by 2
        x *= 2;

        // Now the y length is equal to x over the aspect ratio
        double y = x / aspectRatio;
        // But, we need to divide y by 2 to get the actual length for the triangle
        y /= 2;
        // And finally, the field of view is just the inverse tangent of this value times two
        return 2 * Math.atan(y);
    }
}
