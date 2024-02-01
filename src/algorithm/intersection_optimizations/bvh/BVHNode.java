package algorithm.intersection_optimizations.bvh;

import algorithm.Hittable;
import algorithm.utils.Extent;

import java.util.List;

public class BVHNode {
    private Hittable hittable;
    private List<BVHNode> children;
}
