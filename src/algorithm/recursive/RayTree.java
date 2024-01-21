package algorithm.recursive;

import utilities.Color;
import utilities.Ray;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.Stack;

public class RayTree {
    private RayTreeNode root;
    private int maxBounces;

    private Stack<RenderableObject> mediumStack;
    public RayTree(Ray cameraRay, World world, int maxBounces) {
        root = new RayTreeNode(cameraRay, world, 1, this);
        this.maxBounces = maxBounces;
        this.mediumStack = new Stack<>();
    }

    public Color getPixelColor() {
        return root.getColorContribution();
    }

    public int getMaxTreeDepth() {
        return maxBounces;
    }

    public double getCurrentMediumIOR() {
        if (mediumStack.isEmpty()) {
            return 1;
        }
        return mediumStack.peek().getMaterial().getIndexOfRefraction();
    }

    public void pushMedium(RenderableObject medium) {
        mediumStack.push(medium);
    }

    public void popMedium() {
        mediumStack.pop();
    }
}
