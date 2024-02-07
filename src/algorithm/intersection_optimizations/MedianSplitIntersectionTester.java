package algorithm.intersection_optimizations;

import algorithm.intersection_optimizations.bvh.AutoExtentBVHNode;
import algorithm.intersection_optimizations.bvh.BVHNode;
import algorithm.intersection_optimizations.bvh.BoundingVolumeHierarchy;
import algorithm.intersection_optimizations.bvh.FixedExtentBVHNode;
import algorithm.utils.Extent;
import algorithm.utils.MathUtils;
import algorithm.utils.ObjectDistancePair;
import utilities.Ray;
import utilities.Vector3;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MedianSplitIntersectionTester extends IntersectionTester{
    private BoundingVolumeHierarchy bvh;
    private int maxDepth = 3;

    public MedianSplitIntersectionTester() {
        super();
        this.bvh = new BoundingVolumeHierarchy();
    }

    @Override
    public void addRenderableObject(RenderableObject renderableObject) {
        BVHNode leafNode = new FixedExtentBVHNode(renderableObject);
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
        System.out.println("Building BVH");
        double startTime = System.currentTimeMillis();
        this.bvh.initializeExtents();
        this.splitBVHNode(this.bvh.getRoot(), new HashSet<BVHNode>(), 0);
        System.out.println("BVH built in " + (System.currentTimeMillis() - startTime) + " milliseconds.");

    }

    private void splitBVHNode(BVHNode node, Set<BVHNode> visitedNodes, int depth) {
        if (visitedNodes.contains(node)) {
            return;
        }
        visitedNodes.add(node);

        if (node.isLeafNode() || node.getChildren().size()<=2 || depth >= maxDepth) { // If the node has less than or equal to 2 children, then there are no speed-ups for splitting it further (with this algorithm)
            return;
        }

        // Copy previous children of the node
        List<BVHNode> previousChildrenOfNode = node.getChildren();


        // Compute the new child extents
        int splitAxis = node.getExtent().getLongestAxisIndex();

        Extent nodeExtent = node.getExtent();

        Vector3 nodeExtentMin = nodeExtent.getMin();
        Vector3 nodeExtentMax = nodeExtent.getMax();

        double minLongestAxis = nodeExtentMin.getValueByIndex(splitAxis);
        double maxLongestAxis = nodeExtentMax.getValueByIndex(splitAxis);
        double longestAxisMedianOffset = MathUtils.distance(minLongestAxis, maxLongestAxis) / 2.0;

        Vector3 medianOffset = new Vector3(0, 0, 0);
        medianOffset.setValueByIndex(splitAxis, longestAxisMedianOffset);

        Vector3 maxOfExtent1 = nodeExtentMax.subtractNew(medianOffset);
        Vector3 minOfExtent2 = nodeExtentMin.addNew(medianOffset);

        // The median split algorithm is a recursive algorithm that splits the bounding volume hierarchy into two halves. Initialize these halves.
        Extent extent1 = new Extent(
                nodeExtentMin,
                maxOfExtent1
        );
        Extent extent2 = new Extent(
                minOfExtent2,
                nodeExtentMax
        );

        List<Extent> newExtents = new ArrayList<>();
        newExtents.add(extent1);
        newExtents.add(extent2);

        node.clearChildren();
        node.addChild(new AutoExtentBVHNode());
        node.addChild(new AutoExtentBVHNode());

        for (int i = 0; i < 2; i++) {
            BVHNode newChild = node.getChildren().get(i);
            Extent newExtent = newExtents.get(i);
            for (BVHNode previousChild : previousChildrenOfNode) {
                if (previousChild.getExtent().isIntersectingOtherExtent(newExtent)) {
                    newChild.addChild(previousChild);
                }
            }
        }

        for (BVHNode newChild : node.getChildren()) {
            this.splitBVHNode(newChild, visitedNodes, depth + 1);
        }
    }

    protected BoundingVolumeHierarchy getBVH() {
        return this.bvh;
    }
}
