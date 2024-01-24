package world.scene_objects.light;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;

public class PointLight extends Light {
    public PointLight(Vector3 position, double intensity, Color color) {
        super(position, intensity, color);
    }

    @Override
    public Ray getRayToLight(Vector3 point) {
        Vector3 rayDirection = position.subtractNew(point);
        rayDirection.normalize();
        return new Ray(point, rayDirection);
    }

    @Override
    public double getDistanceToLight(Vector3 point) {
        return position.subtractNew(point).magnitude();
    }

    @Override
    protected double getFallOff(double distance) {
        return 1.0 / (distance * distance);
    }
}
