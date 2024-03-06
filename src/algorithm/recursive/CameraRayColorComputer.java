package algorithm.recursive;

import algorithm.RenderSettings;
import utilities.Color;
import utilities.Ray;
import world.World;

public abstract class CameraRayColorComputer {
    protected Node root;
    protected RenderSettings renderSettings;
    public CameraRayColorComputer(Ray cameraRay, World world, RenderSettings renderSettings) {
        this.renderSettings = renderSettings;
    }

    public abstract Color getCameraRayColor();

    public int getMaxDepth() {
        return this.renderSettings.getMaxBounces();
    }

    public RenderSettings getRenderSettings() {
        return renderSettings;
    }
}
