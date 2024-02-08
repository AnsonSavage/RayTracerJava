package algorithm.recursive;

import algorithm.RenderSettings;
import utilities.Color;
import utilities.Ray;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.Stack;

public class RayTree {
    private RayTreeNode root;


    private RenderSettings renderSettings;
    public RayTree(Ray cameraRay, World world, RenderSettings renderSettings) {
        root = new RayTreeNode(cameraRay, world, 1, this);
        this.renderSettings = renderSettings;
    }

    public Color getPixelColor() {
        return root.getColorContribution();
    }

    public int getMaxTreeDepth() {
        return this.renderSettings.getMaxBounces();
    }
    public RenderSettings getRenderSettings() {
        return renderSettings;
    }
}
