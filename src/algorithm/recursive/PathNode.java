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
            Ray normalRay = new Ray(this.intersectionPoint, this.normalAtIntersection);
            Ray diffuseRay = normalRay.sampleJitteredRay(90); // Sample from the hemisphere... 90 degrees is the max offset from the vector
            PathNode nextPath = new PathNode(diffuseRay, world, nodeDepth + 1, renderSettings);
            // Apparently, because we are sampling from a hemisphere and not a cosine distribution, we must both:
            // 1. fall off by the cosine of the angle and
            // 2. Multiply by the PDF of sampling from a hemisphere (1/(2PI)
            double cosine = Math.abs(this.normalAtIntersection.dot(diffuseRay.getDirection()));
            double hemispherePDF = 1.0 / (2 * Math.PI);
            subsequentRayContribution = nextPath.getColorContribution().multiplyNew(cosine);
        } else if (randomNumber < material.getDiffuseCoefficient() + material.getReflectivity()) {
            // Send a reflection ray
            subsequentRayContribution = computeReflectionContribution(material);
        } else {
            subsequentRayContribution = computeTransmissionContribution(material);
        }

        return currentSurfaceColor.addNew(subsequentRayContribution);
//        if (nodeDepth > 1) {
//        }
//        else {
//            return subsequentRayContribution;
//        }

    }

    @Override
    protected PathNode createNode(Ray ray, World world, int nodeDepth, RenderSettings renderSettings) {
        return new PathNode(ray, world, nodeDepth, renderSettings);
    }
}
