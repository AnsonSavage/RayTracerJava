package algorithm;

import algorithm.recursive.RayTree;
import utilities.Color;
import utilities.Ray;
import world.World;

public class SimpleRecursiveRayTracer extends RayTracer {

    public SimpleRecursiveRayTracer(RenderSettings settings, World world) {
        super(settings, world);
    }

    @Override
    protected Color traceRay(Ray ray) {
        RayTree rayTree = new RayTree(ray, world, settings.getMaxBounces());
        return rayTree.getPixelColor();
    }
}
