package algorithm.recursive;

import algorithm.RenderSettings;
import algorithm.utils.RayOperations;
import utilities.Color;
import utilities.Material;
import utilities.Ray;
import utilities.Vector3;
import world.World;

import java.util.List;

public class PathNode extends Node<PathNode> {
    public PathNode(Ray incomingRay, World world, int nodeDepth, RenderSettings renderSettings) {
        super(incomingRay, world, nodeDepth, renderSettings);
    }

    @Override
    protected Color combineSubsequentBounces(Color currentSurfaceColor) {
        Material material = this.hitObject.getMaterial();
        double coefficientTotal = material.getDiffuseCoefficient() + material.getReflectivity() + material.getTransmission(); // I think we should use reflectivity here instead of the specular coefficient because the specular coefficient just has to do with how prevelant the specular highlight is...
        double randomNumber = Math.random() * coefficientTotal;

        Color subsequentRayContribution = null;
        if (randomNumber < material.getDiffuseCoefficient()) {
            // Send diffuse ray
            // TODO: You may need to make this fall off by the cosine of the angle of the viewing direction
            Ray normalRay = new Ray(this.intersectionPoint, this.normalAtIntersection);
            Ray diffuseRay = normalRay.sampleJitteredRay(180); // Sample from the hemisphere
            PathNode nextPath = new PathNode(diffuseRay, world, nodeDepth, renderSettings);
            subsequentRayContribution = nextPath.getColorContribution();
        } else if (randomNumber < material.getDiffuseCoefficient() + material.getReflectivity()) {
            // Send a reflection ray
            subsequentRayContribution = computeReflectionContribution(material);
        } else {
            subsequentRayContribution = computeTransmissionContribution(material);
        }
        return currentSurfaceColor.addNew(subsequentRayContribution);
    }

    @Override
    protected PathNode createNode(Ray ray, World world, int nodeDepth, RenderSettings renderSettings) {
        return new PathNode(ray, world, nodeDepth, renderSettings);
    }
}
