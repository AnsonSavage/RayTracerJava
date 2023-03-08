package algorithm.recursive;

import algorithm.illumination_model.PhongIlluminationModel;
import algorithm.utils.ObjectDistancePair;
import algorithm.utils.RayOperations;
import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.light.Light;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public class RayTreeNode {
    private Ray incomingRay;
    private RenderableObject hitObject;
    private double incomingRayLength;
    private World world;
    private List<RayTreeNode> children;
    private Vector3 intersectionPoint;
    private Vector3 normalAtIntersection;
    private int nodeDepth;
    private RayTree myTree;
    public RayTreeNode(Ray incomingRay, World world, int nodeDepth, RayTree myTree) {
        this.myTree = myTree;
        this.nodeDepth = nodeDepth;
        this.incomingRay = incomingRay;
        this.world = world;

        // Compute the closest object and the distance to it
        ObjectDistancePair objectDistancePair = RayOperations.getClosestObject(incomingRay, world);
        this.incomingRayLength = objectDistancePair.getDistance();
        this.hitObject = objectDistancePair.getObject();
    }

    public Color getColorContribution() {
        if (this.hitObject == null) {
            return world.getBackground().getColor(null); // NOTE: if you actually cared about this, you would do this after computing reflection ray
        }

        this.intersectionPoint = this.incomingRay.getRayEnd(this.incomingRayLength);
        this.normalAtIntersection = this.hitObject.getNormal(this.intersectionPoint);

        Ray shadowRay = RayOperations.getShadowRays(this.intersectionPoint, world).get(0);
//        List<Ray> shadowRays = RayOperations.getShadowRays(this.intersectionPoint, world);
//
        List<Ray> shadowRays = new ArrayList<>();
        shadowRays.add(shadowRay);
//        List<Light> lightsNotCastingShadows = RayOperations.getNonShadowCastingLights(shadowRays, world, this.hitObject);
//        if (lightsNotCastingShadows.size()==0) {
//            return new Color(0, 0, 0); // In this implementation, we simply return pure black if we're in shadow
//        }
        if (RayOperations.isShadowRayInShadowForLight(shadowRay, world, world.getLights().get(0), this.hitObject)) {
            return new Color(0, 0, 0); // In this implementation, we simply return pure black if we're in shadow
        }

        List<Light> lightsNotCastingShadows = new ArrayList<>();
        lightsNotCastingShadows.add(world.getLights().get(0));

        Color resultantColor = computeIlluminationModel(lightsNotCastingShadows);

        populateChildNodes();
        if (this.children == null) {
            return resultantColor;
        }

        for (RayTreeNode child : this.children) {
            // TODO: This would have to be refactored if we had multiple children
            double reflectivity = this.hitObject.getMaterial().getReflectivity();
            resultantColor.add(child.getColorContribution().multiply(reflectivity)); // TODO: Need to find a way to have falloff
        }

        return resultantColor;

    }

    private Color computeIlluminationModel(List<Light> lightsNotCastingShadows) {
        Vector3 viewingDirection = this.incomingRay.getDirection().multiplyNew(-1);
        PhongIlluminationModel phongIlluminationModel = new PhongIlluminationModel(
                this.hitObject.getMaterial(),
                viewingDirection,
                this.normalAtIntersection,
                this.intersectionPoint,
                lightsNotCastingShadows,
                world.getBackground()
        );

        return phongIlluminationModel.computeColor();
    }

    private void populateChildNodes() {
        if (this.nodeDepth >= this.myTree.getMaxTreeDepth()) {
            return;
        }

        Ray reflectionRay = RayOperations.createReflectionRay(this.incomingRay, this.intersectionPoint, this.normalAtIntersection);
        this.children = new ArrayList<>();
        this.children.add(new RayTreeNode(reflectionRay, this.world, this.nodeDepth+1, myTree));
    }
}
