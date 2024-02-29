package algorithm.utils;

import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.light.Light;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public class RayOperations {

    public static Ray createReflectionRay(Ray incomingRay, Vector3 pointOfIntersection, Vector3 normal) {
        // The incoming ray's direction is multiplied by -1 because we want the direction from the point of intersection to the viewer
        Vector3 reflectedLightDirection = incomingRay.getDirection().multiplyNew(-1).reflect(normal); // The direction of the reflected light
        reflectedLightDirection.normalize();
        Ray reflectionRay = new Ray(pointOfIntersection, reflectedLightDirection);
        reflectionRay.offsetFromOrigin();
        return reflectionRay;
    }

    public static Ray createRefractionRay(Ray incomingRay, Vector3 pointOfIntersection, Vector3 effectiveSurfaceNormal, double IORRatio) {
        // pre-checks
        assert incomingRay.getDirection().isNormalized();
        assert effectiveSurfaceNormal.isNormalized();
        Vector3 incomingDirection = incomingRay.getDirection();

        double cosine = -1 * incomingDirection.dot(effectiveSurfaceNormal);

        Vector3 componentParallelToIncomingRay = incomingDirection.multiplyNew(IORRatio);
        Vector3 componentParallelToNormal = effectiveSurfaceNormal.multiplyNew(IORRatio * cosine - Math.sqrt(1 + (IORRatio * IORRatio) * (cosine * cosine - 1)));

        Vector3 refractedRayDirection = componentParallelToIncomingRay.addNew(componentParallelToNormal);
        refractedRayDirection.normalize();
        Ray refractedRay = new Ray(pointOfIntersection, refractedRayDirection);
        refractedRay.offsetFromOrigin();

        return refractedRay;
    }
}
