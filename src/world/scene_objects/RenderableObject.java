package world.scene_objects;

import utilities.Material;
import utilities.Vector3;

public abstract class RenderableObject extends WorldObject {
    private Material material;

    public RenderableObject(Vector3 position, Material material) {
        super(position);
        this.material = material;
    }

    public abstract Vector3 getNormal(Vector3 positionOnSurface);
}
