package world;

import algorithm.intersection_optimizations.IntersectionTester;
import algorithm.intersection_optimizations.MedianSplitIntersectionTester;
import algorithm.intersection_optimizations.NaiveIntersectionTester;
import algorithm.utils.ObjectDistancePair;
import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
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


    private IntersectionTester intersectionTester;


    public World(List<RenderableObject> renderableObjects, List<Light> lights, Camera camera, Background background, IntersectionTester intersectionTester) {
        this.renderableObjects = renderableObjects;
        this.lights = lights;
        this.camera = camera;
        this.background = background;
        this.intersectionTester = intersectionTester;
    }

    public World(Camera camera) {
        this(
                new ArrayList<RenderableObject>(),
                new ArrayList<Light>(),
                camera,
                new ConstantBackground(new Color(0,0,0), 0.1),
//new NaiveIntersectionTester()
                new MedianSplitIntersectionTester()
        );
    }

    public World() { // Default constructor
        this(null); // Note that if the default constructor is invoked, the camera is set to null
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
        this.intersectionTester.addRenderableObject(object);
    }

    public void addLight(Light light) {
        lights.add(light);
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public ObjectDistancePair getClosestObject(Ray ray) {
        return this.intersectionTester.getClosestObject(ray);
    }

    public List<Ray> getShadowRays(Vector3 pointOfIntersection, Vector3 normalAtIntersection) {
        List<Ray> shadowRays = new ArrayList<>();
        for (Light light : this.getLights()) {
            Vector3 lightDirection = light.getDirectionToLight(pointOfIntersection);
            Ray shadowRay = new Ray(pointOfIntersection, lightDirection);
            shadowRay.offsetFromOrigin(normalAtIntersection); // Move the origin of the shadow ray slightly along the normal of the object
            shadowRays.add(shadowRay);
        }
        assert shadowRays.size() == this.getLights().size();
        return shadowRays;
    }

    public List<Light> getReachableLights(List<Ray> shadowRays) {
        // Note, this code assumes that the shadowRays list is the same order as the world's light list
        assert shadowRays.size() == this.getLights().size();

        List<Light> reachableLights = new ArrayList<>();
        for (int i = 0; i < shadowRays.size(); i++) {
            Ray shadowRay = shadowRays.get(i);
            Light light = this.getLights().get(i);
            if (!this.canRayReachLight(shadowRay, light)) {
                reachableLights.add(light);
            }
        }
        return reachableLights;
    }

    public boolean canRayReachLight(Ray shadowRay, Light light) {
        // TODO: This should be optimized so that we are only intersection testing with the BVH to get the objects that might be hit.
        double distanceToLight = light.getDistanceToLight(shadowRay.getOrigin());
        for (RenderableObject object : this.getRenderableObjects()) {
            if (object.getMaterial().isRefractive()) { // TODO: For now, we're just ignoring refractive objects in shadow calculations
                continue;
            }
            double t = object.getRayIntersectionParameter(shadowRay);
            if (t > 0 && t < distanceToLight) {
                return true;
            }
        }
        return false;
    }

    public IntersectionTester getIntersectionTester() {
        return intersectionTester;
    }
}
