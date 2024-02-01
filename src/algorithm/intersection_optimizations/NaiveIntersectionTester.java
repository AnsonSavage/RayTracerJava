package algorithm.intersection_optimizations;

import algorithm.utils.ObjectDistancePair;
import utilities.Ray;
import world.scene_objects.renderable_objects.RenderableObject;

public class NaiveIntersectionTester extends IntersectionTester{

    @Override
    public ObjectDistancePair getClosestObject(Ray ray) {
        // Set the ray parameter t to be infinity
        double minT = Double.MAX_VALUE;
        RenderableObject closestObject = null;

        for (RenderableObject object : this.renderableObjects) {
            double t = object.getRayIntersectionParameter(ray);
            if (t > 0 && t < minT) {
                minT = t;
                closestObject = object;
            }
        }
        return new ObjectDistancePair(minT, closestObject);
    }

    @Override
    public double getRayParameterAtObjectIntersection(Ray ray, RenderableObject renderableObject) {
        return renderableObject.getRayIntersectionParameter(ray);
    }
}
