package algorithm.recursive;

import utilities.Color;
import utilities.Ray;
import world.World;

public class RayTree {
    private RayTreeNode root;
    private int maxBounces;
    public RayTree(Ray cameraRay, World world, int maxBounces) {
        root = new RayTreeNode(cameraRay, world, 1, this);
        this.maxBounces = maxBounces;
    }

    public Color getPixelColor() {
        return root.getColorContribution();
    }

    public int getMaxTreeDepth() {
        return maxBounces;
    }
}
