package algorithm.intersection_optimizations.bvh;

import algorithm.utils.Extent;
import algorithm.utils.ObjectDistancePair;
import algorithm.utils.ObjectDistancePriorityQueue;
import utilities.Ray;

import java.util.ArrayList;
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

    public Collection<Extent> getExtentsAtDepth(int depth, boolean includeHighestIfDeeper) {
        Collection<Extent> extents = new ArrayList<>();
        getExtentsAtDepthHelper(root, depth, 0, extents, includeHighestIfDeeper);
        return extents;
    }

    private void getExtentsAtDepthHelper(BVHNode node, int targetDepth, int currentDepth, Collection<Extent> extents, boolean includeHighestIfDeeper) {
        // Check if we've reached the target depth, or if we're at the highest depth of a shorter branch
        if (currentDepth == targetDepth || (includeHighestIfDeeper && node.getChildren().isEmpty())) {
            if (node.getExtent() != null) {
                extents.add(node.getExtent());
            }
            return;
        }

        // If we're not at the target depth yet, continue traversing the tree
        for (BVHNode child : node.getChildren()) {
            getExtentsAtDepthHelper(child, targetDepth, currentDepth + 1, extents, includeHighestIfDeeper);
        }
    }
}
