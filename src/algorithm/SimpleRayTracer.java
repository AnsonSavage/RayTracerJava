package algorithm;

import algorithm.illumination_model.PhongIlluminationModel;
import algorithm.utils.ObjectDistancePair;
import utilities.Color;
import utilities.Ray;
import utilities.UVCoordinates;
import utilities.Vector3;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;
import world.scene_objects.renderable_objects.Surface;

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

        return computeColor(ray.getRayEnd(minT), ray.getDirection().multiplyNew(-1), closestObject);
    }

    private Color computeColor(Vector3 pointOfIntersection, Vector3 viewingDirection, RenderableObject object) {
        Vector3 normal = object.getNormal(pointOfIntersection);
        UVCoordinates uvCoordinates = null;

        if (object instanceof Surface) {
            uvCoordinates = ((Surface) object).getTextureCoordinates(pointOfIntersection);
        }

        PhongIlluminationModel phongIlluminationModel = new PhongIlluminationModel(
                object.getMaterial(),
                viewingDirection,
                normal,
                pointOfIntersection,
                world,
                uvCoordinates
        );

        return phongIlluminationModel.computeColor();
    }
}
