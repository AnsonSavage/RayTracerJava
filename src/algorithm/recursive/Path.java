package algorithm.recursive;

import algorithm.RenderSettings;
import utilities.Color;
import utilities.Ray;
import world.World;

public class Path extends CameraRayColorComputer {
    public Path(Ray cameraRay, World world, RenderSettings renderSettings) {
        super(cameraRay, world, renderSettings);
        root = new PathNode(cameraRay, world, 1, renderSettings);
    }

    @Override
    public Color getCameraRayColor() {
        return null;
    }
}
