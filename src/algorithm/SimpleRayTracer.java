package algorithm;

import utilities.Color;
import utilities.Ray;
import world.World;

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

        return new Color(0, 0, 0);// TODO: Implement this
    }
}
