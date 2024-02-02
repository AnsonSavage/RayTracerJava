package algorithm.intersection_optimizations.bvh;

import algorithm.utils.ObjectDistancePair;
import utilities.Ray;

public class BoundingVolumeHierarchy {
    private BVHNode root;

    public boolean isInitialized() {
        return isInitialized;
    }

    private boolean isInitialized;

    public BoundingVolumeHierarchy() {
        this.root = new BVHNode();
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
}
