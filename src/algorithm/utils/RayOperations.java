package algorithm.utils;

import utilities.Color;
import utilities.Ray;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;

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

    public static boolean RayInShadow(Ray shadowRay, World world) { // TODO: implement this
        return false;
    }

    public Color computeIlluminationModel() // TODO:
}
