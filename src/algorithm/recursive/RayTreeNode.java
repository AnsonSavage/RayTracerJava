package algorithm.recursive;

import algorithm.RenderSettings;
import utilities.*;
import world.World;

public class RayTreeNode extends Node<RayTreeNode> {
    // A ray tree node holds some data about the ray on its path to the object it hits, hit object.
    // For example, an initial RayTreeNode object will be a ray coming from the camera to the first object it hits. It will have an IOR Of 1 because it's traveling through air.

    private RayTreeNode reflectionRayTree;
    private RayTreeNode refractionRayTree;
    public RayTreeNode(Ray incomingRay, World world, int nodeDepth, RenderSettings renderSettings) {
        super(incomingRay, world, nodeDepth, renderSettings);
    }

    @Override
    protected Color combineSubsequentBounces(Color currentSurfaceColor, UVCoordinates uvCoordinates) {
        Material material = this.hitObject.getMaterial();
        // Compute reflective contributions
        if (material.getReflectivity() > 0) {
            currentSurfaceColor.add(computeReflectionContribution(material));
        }


        // Compute refractive contributions
        if (material.getTransmission() > 0) {
            Color transmissionRayColor = computeTransmissionContribution(material);
            currentSurfaceColor = Color.blendColors(currentSurfaceColor, transmissionRayColor, material.getTransmission());
        }

        return currentSurfaceColor;
    }

    @Override
    protected RayTreeNode createNode(Ray ray, World world, int nodeDepth, RenderSettings renderSettings) {
        return new RayTreeNode(ray, world, nodeDepth, renderSettings);
    }
}
