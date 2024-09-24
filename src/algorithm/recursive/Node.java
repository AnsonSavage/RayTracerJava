package algorithm.recursive;

import algorithm.RenderSettings;
import algorithm.illumination_model.PhongIlluminationModel;
import algorithm.utils.ObjectDistancePair;
import algorithm.utils.RayOperations;
import utilities.*;
import world.World;
import world.scene_objects.renderable_objects.RenderableObject;
import world.scene_objects.renderable_objects.Surface;

import java.util.List;

public abstract class Node<T extends Node<T>> {
    protected Ray incomingRay;
    protected RenderableObject hitObject;
    protected double incomingRayLength;
    protected World world;

    protected Vector3 intersectionPoint;
    protected Vector3 normalAtIntersection;
    protected int nodeDepth;

    protected RenderSettings renderSettings;

    public Node (Ray incomingRay, World world, int nodeDepth, RenderSettings renderSettings) {
        this.nodeDepth = nodeDepth;
        this.incomingRay = incomingRay;
        this.world = world;
        this.renderSettings = renderSettings;

        // Compute the closest object and the distance to it
        ObjectDistancePair objectDistancePair = world.getClosestObject(incomingRay);
        this.incomingRayLength = objectDistancePair.getDistance();
        this.hitObject = objectDistancePair.getObject();
    }

    protected abstract T createNode (Ray ray, World world, int nodeDepth, RenderSettings renderSettings);

    public Color getColorContribution() {
        if (this.hitObject == null) {
            return world.getBackground().getColor(this.incomingRay.getDirection()); // Get the background in the direction of the incoming ray
        }

        this.intersectionPoint = this.incomingRay.getRayEnd(this.incomingRayLength);
        this.normalAtIntersection = this.hitObject.getNormal(this.intersectionPoint);

        // Check to ensure that the normal and the incoming ray are facing opposite directions
        if (!this.hitObject.getMaterial().isTransmissive() && this.incomingRay.getDirection().dot(normalAtIntersection) > 0) {
            this.normalAtIntersection = this.normalAtIntersection.multiplyNew(-1);
        }

        Material material = this.hitObject.getMaterial();

        UVCoordinates uvCoordinates = null;

        if (this.hitObject instanceof Surface) {
            uvCoordinates = ((Surface) this.hitObject).getTextureCoordinates(this.intersectionPoint);
        }

        Color resultantColor = computeIlluminationModel(material, uvCoordinates);

        if (this.nodeDepth >= this.renderSettings.getMaxBounces()) {
            return resultantColor;
        }

        return combineSubsequentBounces(resultantColor, uvCoordinates);
    }

    /**
     * Takes the color of the surface and combines it with subsequent bounces (either due to the future ray tree or the future ray path)
     *
     * @param currentSurfaceColor The color computed at the surface
     * @param uvCoordinates
     * @return This surface color combined with the color contribution of subsequent bounces
     */
    protected abstract Color combineSubsequentBounces(Color currentSurfaceColor, UVCoordinates uvCoordinates);

    protected Color computeIlluminationModel(Material material, UVCoordinates uvCoordinates) {
        Vector3 viewingDirection = this.incomingRay.getDirection().multiplyNew(-1);

        PhongIlluminationModel phongIlluminationModel = new PhongIlluminationModel(
                material,
                viewingDirection,
                this.normalAtIntersection,
                this.intersectionPoint,
                world,
                this.renderSettings.getAreaLightSamples(),
                uvCoordinates
        );

        return phongIlluminationModel.computeColor();
    }

    protected Color computeReflectionContribution(Material material) {
        double reflectivity = material.getReflectivity();
        double roughness = material.getSquaredReflectiveRoughness();
        Ray reflectionRay = RayOperations.createReflectionRay(
                this.incomingRay,
                this.intersectionPoint,
                this.normalAtIntersection
        );

        if (roughness == 0) { // Shortcut for no roughness, then no stochastic sampling
            T nextNode = createNode(reflectionRay, world, nodeDepth + 1, renderSettings);
            return nextNode.getColorContribution();
        }

        int reflectiveSamples = this.renderSettings.getReflectiveSamples();
        List<Ray> jitteredReflectionRays = reflectionRay.getNJitteredRays(roughness * 90, reflectiveSamples);
        Color reflectionColor = new Color(0, 0, 0);
        for (Ray jitteredReflectionRay : jitteredReflectionRays) {
            T nextNode = createNode(jitteredReflectionRay, this.world, this.nodeDepth+1, renderSettings);
            reflectionColor.add(nextNode.getColorContribution());
        }
        return reflectionColor.multiplyNew(reflectivity * (1.0 / reflectiveSamples));
    }

    protected Color computeTransmissionContribution(Material material) {
        Color transmissionColor = new Color(0, 0, 0);
        int refractiveSamples = this.renderSettings.getRefractiveSamples();

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
            T nextNode = createNode(
                    refractionRay,
                    this.world,
                    this.nodeDepth + 1,
                    renderSettings
            );
            nextNode.getColorContribution();
        }

        List<Ray> jitteredRefractionRays = refractionRay.getNJitteredRays(roughness * 90, refractiveSamples);
        for (Ray jitteredRefractionRay : jitteredRefractionRays) {
            T nextNode = createNode(
                    jitteredRefractionRay,
                    this.world,
                    this.nodeDepth + 1,
                    renderSettings
            );

            transmissionColor.add(nextNode.getColorContribution());
        }
        return transmissionColor.multiplyNew(1.0 / refractiveSamples);
    }
}
