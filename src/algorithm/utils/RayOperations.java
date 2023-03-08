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

    public static List<Ray> getShadowRays(Vector3 pointOfIntersection, World world) {
        List<Ray> shadowRays = new ArrayList<>();
        for (Light light : world.getLights()) {
            Vector3 lightDirection = light.getDirectionToLight(pointOfIntersection);
            Ray shadowRay = new Ray(pointOfIntersection, lightDirection);
            shadowRay.offsetFromOrigin(); // This is done to avoid intersecting with the object that we're shading
            shadowRays.add(shadowRay);
        }
        assert shadowRays.size() == world.getLights().size();
        return shadowRays;
    }

    public static List<Light> getNonShadowCastingLights(List<Ray> shadowRays, World world, RenderableObject objectToAvoid) {
        // Note, this code assumes that the shadowRays list is the same order as the world's light list
        assert shadowRays.size() == world.getLights().size();

        List<Light> lightsNotCastingShadows = new ArrayList<>();
        for (int i = 0; i < shadowRays.size(); i++) {
            Ray shadowRay = shadowRays.get(i);
            Light light = world.getLights().get(i);
            if (!isShadowRayInShadowForLight(shadowRay, world, light, objectToAvoid)) {
                lightsNotCastingShadows.add(light);
            }
        }
        return lightsNotCastingShadows;
    }

    public static boolean isShadowRayInShadowForLight(Ray shadowRay, World world, Light light, RenderableObject objectToAvoid) {
        double distanceToLight = light.getDistanceToLight(shadowRay.getOrigin());
        for (RenderableObject object : world.getRenderableObjects()) {
            if (object == objectToAvoid) {
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
        Vector3 reflectedLightDirection = incomingRay.getDirection().reflect(normal);
        reflectedLightDirection.normalize();
        reflectedLightDirection.multiply(-1); // TODO: This is a random test, but it does seem to be helping
        Ray reflectionRay = new Ray(pointOfIntersection, reflectedLightDirection);
        reflectionRay.offsetFromOrigin();
        return reflectionRay;
    }
}
