package algorithm;

import algorithm.recursive.Path;
import utilities.Color;
import utilities.Ray;
import world.World;

public class MultiSamplePathTracer extends MultiSampleRayTracer {
    public MultiSamplePathTracer(RenderSettings settings, World world, boolean isMultiThreaded) {
        super(settings, world, isMultiThreaded);
    }

    @Override
    protected Color traceRay(Ray ray) {
        Path path = new Path(ray, world, settings);
        return path.getCameraRayColor();
    }
}
