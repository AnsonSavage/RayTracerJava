package world.scene_objects.light;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.scene_objects.renderable_objects.RenderableObject;
import world.scene_objects.renderable_objects.Surface;

public class AreaLight extends InverseSquareFalloffLight {
    private Surface surface;
    public AreaLight(Surface surface, double intensity, Color color) {
        super(null, intensity, color);
        this.surface = surface;
    }

    @Override
    public Ray getRayToLight(Vector3 point) {
        Vector3 rayEnd = this.surface.sampleSurface();
        Vector3 rayDirection = rayEnd.subtractNew(point);
        return new Ray(point, rayDirection); // The ray direction is normalized in the Ray constructor
    }
}
