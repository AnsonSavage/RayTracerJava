package algorithm.intersection_optimizations;

import algorithm.utils.ObjectDistancePair;
import utilities.Ray;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public abstract class IntersectionTester {

    public abstract void addRenderableObject(RenderableObject renderableObject);

    public abstract ObjectDistancePair getClosestObject(Ray ray);

    public abstract void initialize();

    // TODO:
    // We might do something like a function that takes two points and determines if there are any object intersections between them. This would be useful for shadow ray computations, etc.
}
