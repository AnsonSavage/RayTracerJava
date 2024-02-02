package algorithm.intersection_optimizations.bvh;

import algorithm.Hittable;
import algorithm.utils.Extent;
import algorithm.utils.ObjectDistancePair;
import utilities.Ray;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public class BVHNode {
    private RenderableObject renderableObject;
    private Extent extent;
    private List<BVHNode> children; // TODO: the extent of a BVH node should be the convex composition of its children

    public BVHNode() {
        this.renderableObject = null;
        this.extent = null;
        this.children = new ArrayList<>();
    }

    public BVHNode(RenderableObject renderableObject) {
        this();
        this.renderableObject = renderableObject;
    }

    public ObjectDistancePair getClosestObject(Ray ray) {
        if (!this.getExtent().isHit(ray)) {
            return new ObjectDistancePair(Double.MAX_VALUE, null);
        }

        if (this.isLeafNode()) {
            assert this.children.isEmpty();
            double t = this.renderableObject.getRayIntersectionParameter(ray);
            if (t > 0) {
                return new ObjectDistancePair(
                        t,
                        this.renderableObject
                );
            }
            return new ObjectDistancePair(t, null);
        }

        ObjectDistancePair closestObject = new ObjectDistancePair(Double.MAX_VALUE, null); // Currently, the convention is that if ObjectDistancePair has a null as the object, then there was no hit.
        for (BVHNode child : this.children) {
            ObjectDistancePair childClosestObject = child.getClosestObject(ray);
            if (childClosestObject.getObject() != null && childClosestObject.getDistance() < closestObject.getDistance()) {
                closestObject = childClosestObject;
            }
        }

        return closestObject;
    }

    private boolean isLeafNode() {
        return this.renderableObject != null;
    }


    public void setRenderableObject(RenderableObject renderableObject) {
        this.renderableObject = renderableObject;
    }

    public void addChild(BVHNode child) {
        this.extent = null; // Invalidate extent cache
        this.children.add(child);
    }

    public void removeChild(BVHNode child) {
        this.children.remove(child);
    }

    public Extent getExtent() {
        if (this.extent == null) {
            computeExtent();
        }
        return this.extent;
    }

    private void computeExtent() {
        if (isLeafNode()) {
            this.extent = this.renderableObject.getExtent();
        }
        else {
            double minX = Double.MAX_VALUE;
            double minY = Double.MAX_VALUE;
            double minZ = Double.MAX_VALUE;

            double maxX = Double.MIN_VALUE;
            double maxY = Double.MIN_VALUE;
            double maxZ = Double.MIN_VALUE;

            for (BVHNode child: this.children) {
                Extent childExtent = child.getExtent();
                minX = Math.min(minX, childExtent.getMin().getX());
                minY = Math.min(minY, childExtent.getMin().getY());
                minZ = Math.min(minZ, childExtent.getMin().getZ());

                maxX = Math.max(maxX, childExtent.getMax().getX());
                maxY = Math.max(maxY, childExtent.getMax().getY());
                maxZ = Math.max(maxZ, childExtent.getMax().getZ());
            }
            this.extent = new Extent(minX, minY, minZ, maxX, maxY, maxZ);
        }
    }

    protected List<BVHNode> getChildren() {
        return this.children;
    }

    public void initializeExtent() {
        for (BVHNode child: this.children) {
            child.initializeExtent();
        }
        computeExtent();
    }
}
