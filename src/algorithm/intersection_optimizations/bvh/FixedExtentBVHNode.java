package algorithm.intersection_optimizations.bvh;

import algorithm.utils.Extent;
import world.scene_objects.renderable_objects.RenderableObject;

public class FixedExtentBVHNode extends BVHNode {

    public FixedExtentBVHNode(Extent extent) {
        super();
        this.extent = extent;
//        this.extent.scaleUpByEpsilon(); // TODO: well this seemed like it broke everything...
        // Interesting: If this is enabled and maxDepth >= 3, then the testTriangleIntersection test fails. Why?
        if (this.extent.getMin().getX() == this.extent.getMax().getX()) {
            this.extent.getMin().setX(this.extent.getMin().getX() - Extent.EPSILON);
            this.extent.getMax().setX(this.extent.getMax().getX() + Extent.EPSILON);
        }
        if (this.extent.getMin().getY() == this.extent.getMax().getY()) {
            this.extent.getMin().setY(this.extent.getMin().getY() - Extent.EPSILON);
            this.extent.getMax().setY(this.extent.getMax().getY() + Extent.EPSILON);
        }
        if (this.extent.getMin().getZ() == this.extent.getMax().getZ()) {
            this.extent.getMin().setZ(this.extent.getMin().getZ() - Extent.EPSILON);
            this.extent.getMax().setZ(this.extent.getMax().getZ() + Extent.EPSILON);
        }
    }

    public FixedExtentBVHNode(RenderableObject renderableObject) {
        super();
        this.renderableObject = renderableObject;
        this.extent = renderableObject.getExtent();
    }

    @Override
    public Extent getExtent() {
        return this.extent;
    }

    @Override
    public void initializeExtent() {
        // Do nothing
    }
}
