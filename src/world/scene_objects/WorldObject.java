package world.scene_objects;

import utilities.Vector3;

public class WorldObject {
    protected Vector3 position;

    public WorldObject(Vector3 position) {
        this.position = position; // TODO, it would be cool to have a world matrix transformation instead of just a position
    }

    public Vector3 getPosition() {
        return position;
    }
}
