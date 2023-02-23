package world.scene_objects;

import utilities.Color;
import utilities.Vector3;

public class Light extends WorldObject {
    private double intensity;
    private Color color;

    public Light(Vector3 position, Vector3 direction) {
        super(position, direction);
    }

    public Light(Vector3 position) {
        super(position);
    }
}
