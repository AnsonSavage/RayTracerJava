package algorithm.utils;

import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.light.Light;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public class RayOperations {
    /**
     * Returns the closest object that the ray hits, and the distance to that object.
     * @param ray The ray to check
     * @param world The world to check
     * @return The closest object that the ray hits (or null if there is no intersection), and the distance to that object (or Double.MAX_VALUE if the ray doesn't hit anything)
     */
    public static ObjectDistancePair getClosestObject(Ray ray, World world) {
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
        return new ObjectDistancePair(minT, closestObject);
    }

    public static List<Ray> getShadowRays(Vector3 pointOfIntersection, World world, Vector3 normalAtIntersection) {
        List<Ray> shadowRays = new ArrayList<>();
        for (Light light : world.getLights()) {
            Vector3 lightDirection = light.getDirectionToLight(pointOfIntersection);
            Ray shadowRay = new Ray(pointOfIntersection, lightDirection);
            shadowRay.offsetFromOrigin(normalAtIntersection); // Move the origin of the shadow ray slightly along the normal of the object
            shadowRays.add(shadowRay);
        }
        assert shadowRays.size() == world.getLights().size();
        return shadowRays;
    }

    public static List<Light> getReachableLights(List<Ray> shadowRays, World world) {
        // Note, this code assumes that the shadowRays list is the same order as the world's light list
        assert shadowRays.size() == world.getLights().size();

        List<Light> reachableLights = new ArrayList<>();
        for (int i = 0; i < shadowRays.size(); i++) {
            Ray shadowRay = shadowRays.get(i);
            Light light = world.getLights().get(i);
            if (!canRayReachLight(shadowRay, world, light)) {
                reachableLights.add(light);
            }
        }
        return reachableLights;
    }

    public static boolean canRayReachLight(Ray shadowRay, World world, Light light) {
        double distanceToLight = light.getDistanceToLight(shadowRay.getOrigin());
        for (RenderableObject object : world.getRenderableObjects()) {
            if (object.getMaterial().isRefractive()) { // TODO: For now, we're just ignoring refractive objects in shadow calculations
                continue;
            }
            double t = object.getRayIntersectionParameter(shadowRay);
            if (t > 0 && t < distanceToLight) {
                return true;
            }
        }
        return false;
    }
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
