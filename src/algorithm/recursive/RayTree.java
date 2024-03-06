package algorithm.recursive;

import algorithm.RenderSettings;
import utilities.Color;
import utilities.Ray;
import world.World;

public class RayTree extends CameraRayColorComputer {
    public RayTree(Ray cameraRay, World world, RenderSettings renderSettings) {
        super(cameraRay, world, renderSettings);
        root = new RayTreeNode(cameraRay, world, 1, this.renderSettings);
    }

    @Override
    public Color getCameraRayColor() {
        return root.getColorContribution();
    }
}
