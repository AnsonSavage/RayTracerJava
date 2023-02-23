package world;

import world.scene_objects.Camera;
import world.scene_objects.Light;
import world.scene_objects.WorldObject;

import java.util.ArrayList;

public class World {

    private ArrayList<WorldObject> worldObjects;
    private ArrayList<Light> lights;
    private Camera camera;
    double ambientLight;

    public ArrayList<WorldObject> getWorldObjects() {
        return worldObjects;
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public Camera getCamera() {
        return camera;
    }

    public double getAmbientLight() {
        return ambientLight;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
