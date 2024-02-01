package algorithm.intersection_optimizations;

import algorithm.utils.ObjectDistancePair;
import utilities.Ray;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public abstract class IntersectionTester {
    protected List<RenderableObject> renderableObjects;

    public IntersectionTester() {
        this.renderableObjects = new ArrayList<>();
    }

    public IntersectionTester(List<RenderableObject> renderableObjects) {
        this.renderableObjects = renderableObjects;
    }

    public void addRenderableObject(RenderableObject renderableObject) {
        renderableObjects.add(renderableObject);
    }

    public abstract ObjectDistancePair getClosestObject(Ray ray);

    public abstract double getRayParameterAtObjectIntersection(Ray ray, RenderableObject renderableObject);
}
