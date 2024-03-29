package algorithm.recursive;

import algorithm.RenderSettings;
import algorithm.utils.RayOperations;
import utilities.*;
import world.World;
import world.scene_objects.renderable_objects.Surface;

import java.util.List;

public class PathNode extends Node<PathNode> {
    public PathNode(Ray incomingRay, World world, int nodeDepth, RenderSettings renderSettings) {
        super(incomingRay, world, nodeDepth, renderSettings);
    }

    @Override
    public Color getColorContribution() {
        // So, first we're going to get all the usual statistics
        if (this.hitObject == null) {
            return world.getBackground().getColor(this.incomingRay.getDirection()); // Get the background in the direction of the incoming ray
        }

        if (nodeDepth > renderSettings.getMaxBounces()) {
            return new Color(0, 0, 0);
        }

        this.intersectionPoint = this.incomingRay.getRayEnd(this.incomingRayLength);
        this.normalAtIntersection = this.hitObject.getNormal(this.intersectionPoint);

        // Check to ensure that the normal and the incoming ray are facing opposite directions
        Material material = this.hitObject.getMaterial();

        if (!material.isTransmissive() && this.incomingRay.getDirection().dot(normalAtIntersection) > 0) {
            this.normalAtIntersection = this.normalAtIntersection.multiplyNew(-1);
        }

        Color emissionColor = new Color(0, 0, 0);

        if (material instanceof EmissiveMaterial) {
            emissionColor = ((EmissiveMaterial)material).getEmissiveColor();
            return emissionColor; // For now, let's just return this... We can do extra stuff later to blend emissive and other materials
        }



        UVCoordinates uvCoordinates = null;

        if (this.hitObject instanceof Surface) {
            uvCoordinates = ((Surface) this.hitObject).getTextureCoordinates(this.intersectionPoint);
        }


        // Then, we're going to determine what type of ray to shoot next:

        double coefficientTotal = material.getDiffuseCoefficient() + material.getReflectivity() + material.getTransmission(); // I think we should use reflectivity here instead of the specular coefficient because the specular coefficient just has to do with how prevelant the specular highlight is...
        double randomNumber = Math.random() * coefficientTotal;

        Color subsequentRayContribution = null;
        if (randomNumber < material.getDiffuseCoefficient()) {
            // Send diffuse ray
            Ray normalRay = new Ray(this.intersectionPoint, this.normalAtIntersection);
            Ray diffuseRay = normalRay.sampleJitteredRay(180); // Sample from the hemisphere
            PathNode nextPath = new PathNode(diffuseRay, world, nodeDepth + 1, renderSettings);
            Color incomingColor = nextPath.getColorContribution();
            // Apparently, because we are sampling from a hemisphere and not a cosine distribution, we must both:
            // 1. fall off by the cosine of the angle and
            // 2. Multiply by the PDF of sampling from a hemisphere (1/(2PI)

            double cosine = Math.abs(this.normalAtIntersection.dot(diffuseRay.getDirection()));
            double hemispherePDF = 1.0 / (2 * Math.PI);
            Color BRDF = material.getDiffuseColor(uvCoordinates).multiplyNew(1.0 / Math.PI);
            return BRDF.componentWiseMultiply(incomingColor).multiplyNew(cosine * (1.0 / hemispherePDF));
        } else if (randomNumber < material.getDiffuseCoefficient() + material.getReflectivity()) {
            // Send a reflection ray
            Color resultantColor = computeIlluminationModel(material, uvCoordinates);
            subsequentRayContribution = computeReflectionContribution(material);
            return resultantColor.addNew(subsequentRayContribution);
        } else {
            Color resultantColor = computeIlluminationModel(material, uvCoordinates);
            subsequentRayContribution = computeTransmissionContribution(material);
            return resultantColor.addNew(subsequentRayContribution);
        }
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
            Ray diffuseRay = normalRay.sampleJitteredRay(180); // Sample from the hemisphere
            PathNode nextPath = new PathNode(diffuseRay, world, nodeDepth + 1, renderSettings);
            // Apparently, because we are sampling from a hemisphere and not a cosine distribution, we must both:
            // 1. fall off by the cosine of the angle and
            // 2. Multiply by the PDF of sampling from a hemisphere (1/(2PI)
            double cosine = Math.abs(this.normalAtIntersection.dot(this.incomingRay.getDirection()));
            double hemispherePDF = 1.0 / (2 * Math.PI);
            subsequentRayContribution = nextPath.getColorContribution().multiplyNew(cosine);
            return currentSurfaceColor.multiplyNew(1.0 / Math.PI).componentWiseMultiply(subsequentRayContribution.multiplyNew(1.0 / hemispherePDF));
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
