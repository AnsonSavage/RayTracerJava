package algorithm.intersection_optimizations;

import algorithm.utils.ObjectDistancePair;
import algorithm.utils.ObjectDistancePriorityQueue;
import utilities.Ray;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public abstract class IntersectionTester {

    public abstract void addRenderableObject(RenderableObject renderableObject);

    public abstract ObjectDistancePair getClosestObject(Ray ray);

    public abstract void initialize();

    public boolean isRayBlocked(Ray ray) {
        double distance = ray.getOriginalLength();
        return getClosestObject(ray).getDistance() < distance; // TODO: at some point it would be nice to test multiple objects and see if any of them are transparent
    }

    public abstract ObjectDistancePriorityQueue getObjectsInRay(Ray ray);
}
