package algorithm;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.RenderableObject;

/**
 * This is a simple ray tracer that uses the Phong Illumination Model and only computes one ray per pixel.
 * It doesn't even compute shadow rays.
 * It can't compute transparency rays either.
 */
public class SimpleRayTracer extends RayTracer{
    public SimpleRayTracer(RenderSettings settings, World world) {
        super(settings, world);
    }

    @Override
    Color traceRay(Ray ray) {
        // Set the ray parameter t to be infinity
        double minT = Double.MAX_VALUE;
        RenderableObject closestObject = null;

        for (RenderableObject object : world.getRenderableObjects()) {
            double t = object.getRayIntersectionParameter(ray);
            if (t > 0 && t < minT) {
                minT = t;
                closestObject = object;
            }
        }

        if (minT == Double.MAX_VALUE) { // We didn't hit anything, so just return the background of the world
            return world.getBackground().getColor(ray.getDirection());
        }

        assert closestObject != null;


        return new Color(0, 0, 0);// TODO: Implement this
    }

    private Color computeColor(Ray ray, double t, RenderableObject object) {
        Vector3 pointOfIntersection = ray.getRayEnd(t);
        Vector3 normal = object.getNormal(pointOfIntersection);
    }
}
