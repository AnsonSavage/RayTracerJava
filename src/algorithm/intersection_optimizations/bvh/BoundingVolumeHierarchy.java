package algorithm.intersection_optimizations.bvh;

import algorithm.utils.ObjectDistancePair;
import algorithm.utils.ObjectDistancePriorityQueue;
import utilities.Ray;

import java.util.Collection;
import java.util.List;

public class BoundingVolumeHierarchy {
    private BVHNode root;

    public boolean isInitialized() {
        return isInitialized;
    }

    private boolean isInitialized;

    public BoundingVolumeHierarchy() {
        this.root = new AutoExtentBVHNode();
        this.isInitialized = false;
    }

    public BVHNode getRoot() {
        return this.root;
    }

    public ObjectDistancePair getClosestObject(Ray ray) {
        return this.root.getClosestObject(ray);
    }

    public void initializeExtents() {
        this.root.initializeExtent();
        this.isInitialized = true;
    }

    public ObjectDistancePriorityQueue getObjectsInRay(Ray ray) {
        return this.root.getObjectsInRay(ray);
    }
}
