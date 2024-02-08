package algorithm;

import algorithm.recursive.RayTree;
import utilities.Color;
import utilities.Ray;
import world.World;

public class SimpleRecursiveRayTracer extends RayTracer {

    public SimpleRecursiveRayTracer(RenderSettings settings, World world) {
        super(settings, world);
    }

    public SimpleRecursiveRayTracer(RenderSettings settings, World world, boolean isMultithreaded) {
        super(settings, world, isMultithreaded);
    }

    @Override
    protected Color traceRay(Ray ray) {
        RayTree rayTree = new RayTree(ray, world, settings);
        return rayTree.getPixelColor();
    }
}
