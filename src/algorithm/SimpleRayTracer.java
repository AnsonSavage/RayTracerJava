package algorithm;

import utilities.Color;
import utilities.Ray;
import world.World;

public class SimpleRayTracer extends RayTracer{
    public SimpleRayTracer(RenderSettings settings, World world) {
        super(settings, world);
    }

    @Override
    Color traceRay(Ray ray) {
        // Test
//        return new Color(0, 0, 0);
        return new Color(1, 1, 1);
    }
}
