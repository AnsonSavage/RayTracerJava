package world.scene_objects.light;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;

public class SunLight extends Light{
    public SunLight(Vector3 position, Vector3 direction, double intensity, Color color) {
        super(position, direction, intensity, color);
    }

    @Override
    public Ray getRayToLight(Vector3 point) {
        Vector3 rayDirection = direction.multiplyNew(-1); // Ray direction is independent of the starting point for a sun style light
        rayDirection.normalize();
        return new Ray(point, rayDirection);
    }

    @Override
    public double getDistanceToLight(Vector3 point) {
        return Double.POSITIVE_INFINITY;
    }
}
