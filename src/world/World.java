package world;

import algorithm.intersection_optimizations.IntersectionTester;
import algorithm.intersection_optimizations.NaiveIntersectionTester;
import algorithm.utils.ObjectDistancePair;
import utilities.Color;
import utilities.Material;
import utilities.Ray;
import utilities.Vector3;
import world.background.Background;
import world.background.ConstantBackground;
import world.scene_objects.Camera;
import world.scene_objects.light.AreaLight;
import world.scene_objects.renderable_objects.AxisAlignedRectangularPrism;
import world.scene_objects.renderable_objects.RenderableObject;
import world.scene_objects.light.Light;

import java.util.ArrayList;
import java.util.List;

public class World {


    private List<RenderableObject> renderableObjects;
    private List<Light> nonAreaLights;
    private List<AreaLight> areaLights;
    private Camera camera;
    Background background;


    private IntersectionTester intersectionTester;


    public World(List<RenderableObject> renderableObjects, List<Light> nonAreaLights, List<AreaLight> areaLights, Camera camera, Background background, IntersectionTester intersectionTester) {
        this.renderableObjects = renderableObjects;
        this.nonAreaLights = nonAreaLights;
        this.areaLights = areaLights;
        this.camera = camera;
        this.background = background;
        this.intersectionTester = intersectionTester;
    }

    public World(Camera camera) {
        this(
                new ArrayList<RenderableObject>(),
                new ArrayList<Light>(),
                new ArrayList<>(),
                camera,
                new ConstantBackground(new Color(0,0,0), 0.1),
                new NaiveIntersectionTester()
        );
    }

    public World(Camera camera, IntersectionTester intersectionTester) {
        this(
                new ArrayList<RenderableObject>(),
                new ArrayList<Light>(),
                new ArrayList<>(),
                camera,
                new ConstantBackground(new Color(0,0,0), 0.1),
                intersectionTester
       );
        }

    public World() { // Default constructor
        this(null); // Note that if the default constructor is invoked, the camera is set to null
    }

    public List<Light> getNonAreaLights() {
        return nonAreaLights;
    }

    public List<Light> getAllLights() {
        List<Light> allLights = new ArrayList<>();
        allLights.addAll(nonAreaLights);
        allLights.addAll(areaLights);
        return allLights;
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
        if (light instanceof AreaLight) {
            this.areaLights.add((AreaLight) light);
        } else {
            this.nonAreaLights.add(light);
        }
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public ObjectDistancePair getClosestObject(Ray ray) {
        return this.intersectionTester.getClosestObject(ray);
    }

    public boolean isRayBlocked(Ray ray) {
//        return this.isObjectBetweenTwoPoints(ray.getOrigin(), ray.getRayEnd(ray.getOriginalLength()));
        return this.intersectionTester.isRayBlocked(ray);
    }

//    public boolean isObjectBetweenTwoPoints(Vector3 startPoint, Vector3 endPoint) {
//        // TODO
//    }

    public IntersectionTester getIntersectionTester() {
        return intersectionTester;
    }

    public World generateBoundingBoxWorld(IntersectionTester intersectionTester) {
        List<AxisAlignedRectangularPrism> boundingBoxes = new ArrayList<>();
        for (RenderableObject object : this.getRenderableObjects()) {
            Material randomDiffuseMaterial = new Material(
                    0.1,
                    0.9,
                    0.0,
                    0.0,
                    0.0,
                    new Color (Math.random(), Math.random(), Math.random()),
                    new Color (0,0,0)
            );
            AxisAlignedRectangularPrism boundingBox = new AxisAlignedRectangularPrism(object.getExtent(), randomDiffuseMaterial);
            boundingBoxes.add(boundingBox);
        }

        World world = new World(this.camera, intersectionTester);
        for (AxisAlignedRectangularPrism boundingBox : boundingBoxes) {
            world.addRenderableObject(boundingBox);
        }

        // Copy lights and background
        for (Light light : this.getNonAreaLights()) {
            world.addLight(light);
        }

        world.setBackground(this.getBackground());
        return world;
    }

    public List<AreaLight> getAreaLights() {
        return areaLights;
    }
}
