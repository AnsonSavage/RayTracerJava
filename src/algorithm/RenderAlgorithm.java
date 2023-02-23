package algorithm;

import utilities.Image;
import world.World;

public abstract class RenderAlgorithm {
    protected RenderSettings settings;
    protected World world;
    protected Image image;

    public RenderAlgorithm(RenderSettings settings, World world) {
        this.settings = settings;
        this.world = world;
    }

    public void render() {
        renderImplementation();
    }

    protected abstract void renderImplementation();
}
