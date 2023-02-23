package world.scene_objects;

import utilities.Material;
import utilities.Vector3;

public class RenderableObject extends WorldObject {
    private Material material;

    public RenderableObject(Vector3 position, Material material) {
        super(position);
        this.material = material;
    }
}
