package algorithm.intersection_optimizations.bvh;

import algorithm.utils.Extent;
import world.scene_objects.renderable_objects.RenderableObject;

public class FixedExtentBVHNode extends BVHNode {

    public FixedExtentBVHNode(Extent extent) {
        super();
        this.extent = extent;
        this.extent.scaleUpByEpsilon(); // TODO: well this seemed like it broke everything...
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
