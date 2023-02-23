package world;

import world.scene_objects.Camera;
import world.scene_objects.RenderableObject;
import world.scene_objects.light.Light;

import java.util.ArrayList;
import java.util.List;

public class World {

    private List<RenderableObject> renderableObjects;
    private List<Light> lights;
    private Camera camera;
    double ambientLight;


    public World() { // Default constructor
        this(null); // Note that if the default constructor is invoked, the camera is set to null
    }
    public World(Camera camera) {
        this(
                new ArrayList<RenderableObject>(),
                new ArrayList<Light>(),
                camera,
                0.0
        );
    }

    public World(List<RenderableObject> renderableObjects, List<Light> lights, Camera camera, double ambientLight) {
        this.renderableObjects = renderableObjects;
        this.lights = lights;
        this.camera = camera;
        this.ambientLight = ambientLight;
    }



    public List<Light> getLights() {
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

    public void setAmbientLight(double ambientLight) {
        this.ambientLight = ambientLight;
    }
}
