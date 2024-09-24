package algorithm.intersection_optimizations.bvh;

import algorithm.Hittable;
import algorithm.utils.Extent;
import algorithm.utils.ObjectDistancePair;
import algorithm.utils.ObjectDistancePriorityQueue;
import utilities.Ray;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public abstract class BVHNode {
    public RenderableObject getRenderableObject() {
        return renderableObject;
    }
    protected RenderableObject renderableObject = null;
    protected Extent extent = null;
    protected List<BVHNode> children = new ArrayList<>();

    public BVHNode() {
        this.renderableObject = null;
        this.extent = null;
        this.children = new ArrayList<>();
    }


    public ObjectDistancePair getClosestObject(Ray ray) {
        boolean useQueue = false;
        if (useQueue) {
            ObjectDistancePriorityQueue queue = this.getObjectsInRay(ray);
            if (queue == null) {
                return new ObjectDistancePair(Double.POSITIVE_INFINITY, null);
            }
            return queue.poll();
        } else {
            if (!this.getExtent().isHit(ray)) {
                return new ObjectDistancePair(Double.POSITIVE_INFINITY, null);
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

            ObjectDistancePair closestObject = new ObjectDistancePair(Double.POSITIVE_INFINITY, null); // Currently, the convention is that if ObjectDistancePair has a null as the object, then there was no hit.
            for (BVHNode child : this.children) {
                ObjectDistancePair childClosestObject = child.getClosestObject(ray);
                if (childClosestObject.getObject() != null && childClosestObject.getDistance() < closestObject.getDistance()) {
                    closestObject = childClosestObject;
                }
            }

            return closestObject;
        }
    }
    public boolean isLeafNode() {
        return this.renderableObject != null;
    }


    public void setRenderableObject(RenderableObject renderableObject) {
        this.renderableObject = renderableObject;
    }

    public void addChild(BVHNode child) {
        this.children.add(child);
    }

    public void removeChild(BVHNode child) {
        this.children.remove(child);
    }

    public abstract Extent getExtent();


    public List<BVHNode> getChildren() {
        return this.children;
    }

    public void clearChildren() {
        this.children = new ArrayList<>();
    }

    public abstract void initializeExtent();

    public ObjectDistancePriorityQueue getObjectsInRay(Ray ray) {
        if (!this.getExtent().isHit(ray)) {
            return null;
        }

        if (this.isLeafNode()) {
            assert this.children.isEmpty();
            double t = this.renderableObject.getRayIntersectionParameter(ray);
            if (t > 0) {
                ObjectDistancePriorityQueue queue = new ObjectDistancePriorityQueue();
                queue.add(new ObjectDistancePair(
                        t,
                        this.renderableObject
                ));
                return queue;
            }
            return null;
        }

        ObjectDistancePriorityQueue queue = new ObjectDistancePriorityQueue();
        for (BVHNode child : this.children) {
            ObjectDistancePriorityQueue childClosestObjects = child.getObjectsInRay(ray);
            if (childClosestObjects != null) {
                queue.merge(childClosestObjects);
            }
        }

        if (queue.size() == 0) {
            return null;
        }

        return queue;
    }
}
