package algorithm.intersection_optimizations;

import algorithm.intersection_optimizations.bvh.BVHNode;
import algorithm.intersection_optimizations.bvh.BoundingVolumeHierarchy;
import algorithm.utils.ObjectDistancePair;
import utilities.Ray;
import world.scene_objects.renderable_objects.RenderableObject;

public class MedianSplitIntersectionTester extends IntersectionTester{
    private BoundingVolumeHierarchy bvh;

    public MedianSplitIntersectionTester() {
        super();
        this.bvh = new BoundingVolumeHierarchy();
    }

    @Override
    public void addRenderableObject(RenderableObject renderableObject) {
        BVHNode leafNode = new BVHNode(renderableObject);
        this.bvh.getRoot().addChild(
                leafNode
        );
    }

    @Override
    public ObjectDistancePair getClosestObject(Ray ray) {
        if (!this.bvh.isInitialized()) {
            this.bvh.initializeExtents();
        }
        return this.bvh.getClosestObject(ray);
    }

    @Override
    public void initialize() {
        this.buildBVH();
    }

    private void buildBVH() {
        this.bvh.initializeExtents();
    }

    protected BoundingVolumeHierarchy getBVH() {
        return this.bvh;
    }
}
