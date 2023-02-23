package world.scene_objects;

import utilities.Vector3;

public class WorldObject {
    protected Vector3 position;
    protected Vector3 orientation;

    public WorldObject(Vector3 position, Vector3 orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    public WorldObject(Vector3 position) {
        this(position, new Vector3(0, 0, 1));
    }

    public void setOrientation(Vector3 orientation) {
        this.orientation = orientation;
    }

    public Vector3 getPosition() {
        return position;
    }
}
