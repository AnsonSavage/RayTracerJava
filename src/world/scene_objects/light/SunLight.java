package world.scene_objects.light;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;

public class SunLight extends Light{
    protected Vector3 direction;
    public SunLight(Vector3 position, Vector3 direction, double intensity, Color color) {
        super(position, intensity, color);
        this.direction = direction;
    }

    @Override
    public Ray getRayToLight(Vector3 point) {
        Vector3 rayDirection = direction.multiplyNew(-1); // Ray direction is independent of the starting point for a sun style light
        return new Ray(point, rayDirection);
    }
    @Override
    protected double getFallOff(double distance) {
        return 1.0;
    }
}
