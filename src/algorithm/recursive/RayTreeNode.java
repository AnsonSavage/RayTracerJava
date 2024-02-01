package algorithm.recursive;

import algorithm.illumination_model.PhongIlluminationModel;
import algorithm.utils.ObjectDistancePair;
import algorithm.utils.RayOperations;
import utilities.Color;
import utilities.Material;
import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.light.Light;
import world.scene_objects.renderable_objects.RenderableObject;

import java.util.ArrayList;
import java.util.List;

public class RayTreeNode {
    // A ray tree node holds some data about the ray on its path to the object it hits, hit object.
    // For example, an initial RayTreeNode object will be a ray coming from the camera to the first object it hits. It will have an IOR Of 1 because it's traveling through air.

    private Ray incomingRay;
    private RenderableObject hitObject;
    private double incomingRayLength;
    private World world;

    private RayTreeNode reflectionRayTree; // It has two leaves which point to more nodes
    private RayTreeNode refractionRayTree;
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
        ObjectDistancePair objectDistancePair = world.getClosestObject(incomingRay);
        this.incomingRayLength = objectDistancePair.getDistance();
        this.hitObject = objectDistancePair.getObject();
    }

    public Color getColorContribution() {
        if (this.hitObject == null) {
            return world.getBackground().getColor(null); // NOTE: if you actually cared about this, you would do this after computing reflection ray
        }

        this.intersectionPoint = this.incomingRay.getRayEnd(this.incomingRayLength);
        this.normalAtIntersection = this.hitObject.getNormal(this.intersectionPoint);

        List<Ray> shadowRays = world.getShadowRays(this.intersectionPoint, this.normalAtIntersection);

        // So here's the thing... We have a number of things going on.
        // Shadow rays (The lack of light... Depends on # of lights, etc.)
        // Reflection Rays (mirror reflections)
        // The Phong illumination model, which needs all the lights that aren't casting shadows
        // Refraction rays

        List<Light> reachableLights = world.getReachableLights(shadowRays);

        Color resultantColor = computeIlluminationModel(reachableLights);

        if (this.nodeDepth >= this.myTree.getMaxTreeDepth()) {
            return resultantColor;
        }

        // Compute reflection ray
        Material material = this.hitObject.getMaterial();

        double reflectivity = material.getReflectivity();
        if (reflectivity > 0) {
            Ray reflectionRay = RayOperations.createReflectionRay(
                    this.incomingRay,
                    this.intersectionPoint,
                    this.normalAtIntersection
            );
            this.reflectionRayTree = new RayTreeNode(reflectionRay, this.world, this.nodeDepth+1, myTree);
            resultantColor.add(this.reflectionRayTree.getColorContribution().multiplyNew(reflectivity));
        }


        // Compute refraction ray
        // First test to see if the material is refractive
        double transmission = material.getTransmission();
        if (transmission > 0) {

            // Set the IOR RATIO
            double currentIOR;
            double nextIOR;
            Vector3 effectiveSurfaceNormal;

            // Determine whether we are entering or exiting the object
            if (this.normalAtIntersection.dot(this.incomingRay.getDirection()) > 0) { // Exiting
//                assert this.myTree.getCurrentMediumIOR() == material.getIndexOfRefraction();
                currentIOR = material.getIndexOfRefraction();
//                this.myTree.popMedium();
//                nextIOR = this.myTree.getCurrentMediumIOR();
                nextIOR = 1;
                effectiveSurfaceNormal = this.normalAtIntersection.multiplyNew(-1);
            } else { // Entering
//                assert this.myTree.getCurrentMediumIOR() == 1;
//                currentIOR = this.myTree.getCurrentMediumIOR();
                currentIOR = 1;
//                this.myTree.pushMedium(this.hitObject);
                nextIOR = material.getIndexOfRefraction();
                effectiveSurfaceNormal = this.normalAtIntersection;
            }

            double IORRatio = currentIOR / nextIOR;

            Ray refractionRay = RayOperations.createRefractionRay(
                    this.incomingRay,
                    this.intersectionPoint,
                    effectiveSurfaceNormal,
                    IORRatio
            );
            this.refractionRayTree = new RayTreeNode(
                    refractionRay,
                    this.world,
                    this.nodeDepth + 1,
                    myTree
            );

            // Now, we need to set the resultant color to be a lerp between what it was before and the results of the transmission color
            Color transmissionRayColor = this.refractionRayTree.getColorContribution();
            resultantColor = resultantColor.multiplyNew(1 - transmission).addNew(transmissionRayColor.multiplyNew(transmission)); // Convex composition
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
}
