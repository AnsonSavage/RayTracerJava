package algorithm.intersection_optimizations.bvh;

import algorithm.utils.Extent;
import world.scene_objects.renderable_objects.RenderableObject;

public class AutoExtentBVHNode extends BVHNode{

    public AutoExtentBVHNode() {
        super();
    }

    public AutoExtentBVHNode(RenderableObject renderableObject) {
        super();
        this.renderableObject = renderableObject;
    }
    @Override
    public void addChild(BVHNode child) {
        this.extent = null;
        this.children.add(child);
    }

    public Extent getExtent() {
        if (this.extent == null) {
            computeExtent();
        }
        return this.extent;
    }

    @Override
    public void initializeExtent() {
        for (BVHNode child: this.children) {
            child.initializeExtent();
        }
        computeExtent();
    }

    protected void computeExtent() {
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
}
