package algorithm.intersection_optimizations;

import algorithm.utils.ObjectDistancePair;
import algorithm.utils.ObjectDistancePriorityQueue;
import utilities.Ray;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public class NaiveIntersectionTester extends IntersectionTester{
    private List<RenderableObject> renderableObjects = new ArrayList<>();

    @Override
    public void addRenderableObject(RenderableObject renderableObject) {
        this.renderableObjects.add(renderableObject);
    }

    @Override
    public ObjectDistancePair getClosestObject(Ray ray) {
        // Set the ray parameter t to be infinity
        double minT = Double.POSITIVE_INFINITY;
        RenderableObject closestObject = null;

        for (RenderableObject object : this.renderableObjects) {
            double t = object.getRayIntersectionParameter(ray);
            if (t > 0 && t < minT) {
                minT = t;
                closestObject = object;
            }
        }
        return new ObjectDistancePair(minT, closestObject);
    }

    @Override
    public void initialize() {
        // Do nothing in the naive approach
    }

    @Override
    public ObjectDistancePriorityQueue getObjectsInRay(Ray ray) {
        ObjectDistancePriorityQueue objectDistancePriorityQueue = new ObjectDistancePriorityQueue();
        for (RenderableObject object : this.renderableObjects) {
            double t = object.getRayIntersectionParameter(ray);
            if (t > 0 && t < ray.getOriginalLength()) {
                objectDistancePriorityQueue.add(new ObjectDistancePair(t, object));
            }
        }
        return objectDistancePriorityQueue;
    }
}
