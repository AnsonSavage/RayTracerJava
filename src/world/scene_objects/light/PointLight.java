package world.scene_objects.light;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;

public class PointLight extends InverseSquareFalloffLight {
    public PointLight(Vector3 position, double intensity, Color color) {
        super(position, intensity, color);
    }

    @Override
    public Ray getRayToLight(Vector3 point) {
        Vector3 rayDirection = position.subtractNew(point);
        return new Ray(point, rayDirection); // Currently the ray direction is normalized in the Ray constructor
    }
}
