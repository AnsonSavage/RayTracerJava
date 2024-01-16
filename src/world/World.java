package world;

import utilities.Color;
import world.background.Background;
import world.background.ConstantBackground;
import world.scene_objects.Camera;
import world.scene_objects.renderable_objects.RenderableObject;
import world.scene_objects.light.Light;

import java.util.ArrayList;
import java.util.List;

public class World {


    private List<RenderableObject> renderableObjects;
    private List<Light> lights;
    private Camera camera;
    Background background;


    public World() { // Default constructor
        this(null); // Note that if the default constructor is invoked, the camera is set to null
    }
    public World(Camera camera) {
        this(
                new ArrayList<RenderableObject>(),
                new ArrayList<Light>(),
                camera,
                new ConstantBackground(new Color(0,0,0), 0.1)
        );
    }

    public World(List<RenderableObject> renderableObjects, List<Light> lights, Camera camera, Background background) {
        this.renderableObjects = renderableObjects;
        this.lights = lights;
        this.camera = camera;
        this.background = background;
    }



    public List<Light> getLights() {
        return lights;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public List<RenderableObject> getRenderableObjects() {
        return renderableObjects;
    }

    public Background getBackground() {
        return background;
    }

    public void addRenderableObject(RenderableObject object) {
        renderableObjects.add(object);
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public void setBackground(Background background) {
        this.background = background;
    }
}
