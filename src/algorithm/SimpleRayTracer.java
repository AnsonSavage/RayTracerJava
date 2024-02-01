package algorithm;

import algorithm.illumination_model.PhongIlluminationModel;
import algorithm.utils.ObjectDistancePair;
import algorithm.utils.RayOperations;
import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;

/**
 * This is a simple ray tracer that uses the Phong Illumination Model and only computes one ray per pixel.
 * It doesn't even compute shadow rays.
 * It can't compute transparency rays either.
 */
public class SimpleRayTracer extends RayTracer {

    public SimpleRayTracer(RenderSettings settings, World world) {
        super(settings, world);
    }
    public SimpleRayTracer(RenderSettings settings, World world, boolean isMultiThreaded) {
        super(settings, world, isMultiThreaded);
    }


    @Override
    Color traceRay(Ray ray) {

        ObjectDistancePair closestObjectDistancePair = world.getClosestObject(ray);
        double minT = closestObjectDistancePair.getDistance();
        RenderableObject closestObject = closestObjectDistancePair.getObject();

        if (closestObject == null) { // We didn't hit anything, so just return the background of the world
            return world.getBackground().getColor(ray.getDirection());
        }

        return computeColor(ray, minT, closestObject);
    }

    private Color computeColor(Ray ray, double t, RenderableObject object) {
        Vector3 pointOfIntersection = ray.getRayEnd(t);
        Vector3 normal = object.getNormal(pointOfIntersection);
        PhongIlluminationModel phongIlluminationModel = new PhongIlluminationModel(
                object.getMaterial(),
                ray.getDirection().multiplyNew(-1), // The direction from the point to the viewer
                normal,
                pointOfIntersection,
                world.getLights(),
                world.getBackground()
        );

        return phongIlluminationModel.computeColor();
    }
}
