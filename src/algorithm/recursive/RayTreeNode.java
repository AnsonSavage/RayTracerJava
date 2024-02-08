package algorithm.recursive;

import algorithm.illumination_model.PhongIlluminationModel;
import algorithm.utils.ObjectDistancePair;
import algorithm.utils.RayOperations;
import utilities.Color;
import utilities.Material;
import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;

public class RayTreeNode {
    // A ray tree node holds some data about the ray on its path to the object it hits, hit object.
    // For example, an initial RayTreeNode object will be a ray coming from the camera to the first object it hits. It will have an IOR Of 1 because it's traveling through air.

    private Ray incomingRay;
    private RenderableObject hitObject;
    private double incomingRayLength;
    private World world;

    private RayTreeNode reflectionRayTree;
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
            return world.getBackground().getColor(this.incomingRay.getDirection()); // Get the background in the direction of the incoming ray
        }

        this.intersectionPoint = this.incomingRay.getRayEnd(this.incomingRayLength);
        this.normalAtIntersection = this.hitObject.getNormal(this.intersectionPoint);

        Material material = this.hitObject.getMaterial();

        Color resultantColor = computeIlluminationModel(material);

        if (this.nodeDepth >= this.myTree.getMaxTreeDepth()) {
            return resultantColor;
        }


        // Compute reflective contributions
        if (material.getReflectivity() > 0) {
            resultantColor.add(computeReflectionContribution(material));
        }


        // Compute refractive contributions
        if (material.getTransmission() > 0) {
            Color transmissionRayColor = computeTransmissionContribution(material);
            resultantColor = resultantColor.multiplyNew(1 - material.getTransmission()).addNew(transmissionRayColor.multiplyNew(material.getTransmission())); // Convex composition
        }

        return resultantColor;

    }

    private Color computeTransmissionContribution(Material material) {
        Color transmissionRayColor = new Color(0, 0, 0);
        int refractiveSamples = this.myTree.getRenderSettings().getRefractiveSamples();

        double roughness = material.getSquaredTransmissiveRoughness();

        // Set the IOR RATIO
        double currentIOR;
        double nextIOR;
        Vector3 effectiveSurfaceNormal;

        // Determine whether we are entering or exiting the object
        if (this.normalAtIntersection.dot(this.incomingRay.getDirection()) > 0) { // Exiting
            currentIOR = material.getIndexOfRefraction();
            nextIOR = 1; // TODO: could never quite figure out the trick to figure out the IOR of the medium we're entering after exiting the current medium
            effectiveSurfaceNormal = this.normalAtIntersection.multiplyNew(-1);
        } else { // Entering
            currentIOR = 1;
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

        if (roughness == 0) { // Shortcut for no roughness, then no stochastic sampling
            this.refractionRayTree = new RayTreeNode(
                    refractionRay,
                    this.world,
                    this.nodeDepth + 1,
                    myTree
            );
            return this.refractionRayTree.getColorContribution();
        }

        for (int i = 0; i < refractiveSamples; i++) {
            Ray jitteredRefractionRay = refractionRay.sampleJitteredRay(roughness * 180); // If sqauaredReflectiveRoughness is 0, this will return the same ray
            this.refractionRayTree = new RayTreeNode(
                    jitteredRefractionRay,
                    this.world,
                    this.nodeDepth + 1,
                    myTree
            );

            transmissionRayColor.add(this.refractionRayTree.getColorContribution());
        }
        return transmissionRayColor.multiplyNew(1.0 / refractiveSamples);
    }

    private Color computeReflectionContribution(Material material) {
        Color resultantColor = new Color(0, 0, 0);
        double reflectivity = material.getReflectivity();
        Ray reflectionRay = RayOperations.createReflectionRay(
                this.incomingRay,
                this.intersectionPoint,
                this.normalAtIntersection
        );
        this.reflectionRayTree = new RayTreeNode(reflectionRay, this.world, this.nodeDepth+1, myTree);
        resultantColor.add(this.reflectionRayTree.getColorContribution().multiplyNew(reflectivity));
        return resultantColor;
    }

    private Color computeIlluminationModel(Material material) {
        Vector3 viewingDirection = this.incomingRay.getDirection().multiplyNew(-1);

        PhongIlluminationModel phongIlluminationModel = new PhongIlluminationModel(
                material,
                viewingDirection,
                this.normalAtIntersection,
                this.intersectionPoint,
                world,
                this.myTree.getRenderSettings().getAreaLightSamples()
        );

        return phongIlluminationModel.computeColor();
    }
}
