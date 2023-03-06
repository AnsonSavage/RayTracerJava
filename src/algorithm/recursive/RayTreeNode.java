package algorithm.recursive;

import algorithm.utils.ObjectDistancePair;
import algorithm.utils.RayOperations;
import utilities.Color;
import utilities.Ray;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;

public class RayTreeNode {
    private Ray incomingRay;
    private RenderableObject hitObject;
    private double incomingRayLength;
    private World world;

    public RayTreeNode(Ray incomingRay, World world) {
        this.incomingRay = incomingRay;
        this.world = world;
        ObjectDistancePair objectDistancePair = RayOperations.getClosestObject(incomingRay, world);
        this.incomingRayLength = objectDistancePair.getDistance();
        this.hitObject = objectDistancePair.getObject();
    }

    public Color getColorContribution() {
        Ray shadowRay = null;
        Ray reflectionRay = null;
        if (this.hitObject == null) {
            return world.getBackground().getColor(null); // Todo: if you actually cared about this, you would do this after computing reflection ray
        }

        shadowRay = computeShadowRay(incomingRay);

        if (RayOperations.RayInShadow(shadowRay, world)) {
            return new Color(0, 0, 0); // In this implementation, we simply return pure black if we're in shadow
        }

        reflectionRay = computeReflectionRay(incomingRay);

    }


    private Ray computeReflectionRay(Ray incomingRay) {

    }

    private Ray computeShadowRay(Ray incomingRay) {

    }
}
