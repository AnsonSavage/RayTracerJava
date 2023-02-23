package world.scene_objects;

import utilities.Vector3;

public class Camera extends WorldObject {
    private double fieldOfViewX;
    private double fieldOfViewY;
    private double focalLength;
    private Vector3 up;

    public Camera() {
    }

    public Vector3 getLookAt() {
        this.orientation.normalize();
        return this.orientation;
    }

    public Vector3 getImagePlaneCenter() {
        Vector3 lookAtVector = this.getLookAt();
        return this.position.addNew(lookAtVector.multiplyNew(this.focalLength));
    }

    double getImagePlaneLengthX() {
        return 2 * this.focalLength * Math.tan(this.fieldOfViewX / 2);
    }

    double getImagePlaneLengthY() {
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

}
