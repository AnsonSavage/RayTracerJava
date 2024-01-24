package algorithm.illumination_model;

import utilities.Color;
import utilities.Material;
import utilities.Vector3;
import world.background.Background;
import world.scene_objects.light.Light;

import java.util.List;

public class PhongIlluminationModel {
    private Material material;
    private Vector3 viewingDirection;
    private Vector3 normal;
    private Vector3 positionOnSurface;
    private List<Light> lights;
    private Background background;

    /**
     * Creates a new Phong illumination model
     * @param material The material of the object whose color is being computed
     * @param viewingDirection The direction from the point on the object to the camera
     * @param normal The surface normal of the object at the point on the object
     * @param positionOnSurface The position on the object that the ray has intersected
     * @param lights The list of lights in the scene
     * @param background The background of the scene
     */
    public PhongIlluminationModel(Material material, Vector3 viewingDirection, Vector3 normal, Vector3 positionOnSurface, List<Light> lights, Background background) {
        this.material = material;
        this.viewingDirection = viewingDirection;
        this.normal = normal;
        this.positionOnSurface = positionOnSurface;
        this.lights = lights;
        this.background = background;
    }

    private Light currentLight;

    private Color computeAmbientComponent() {
        // k_a * I_a * O_d
        double ambientCoefficient = material.getAmbientCoefficient();
        double ambientIntensity = background.getAmbientIntensity();
        Color ambientColor = background.getColor(viewingDirection);
        return ambientColor.componentWiseMultiply(material.getDiffuseColor().multiplyNew(ambientCoefficient * ambientIntensity)); // Apparently the ambient light is only dependent on the material's diffuse color.
    }

    private Color computeSpecularComponent() {
        // k_s * I_p * O_s * max(0, (R dot V)^kgls)
        double specularCoefficient = material.getSpecularCoefficient();
        Vector3 reflectedLightDirection = currentLight.getDirectionToLight(positionOnSurface).reflect(normal);
        double viewingAngleDependentIntensityFactor = Math.pow(Math.max(0.0, viewingDirection.dot(reflectedLightDirection)), material.getSpecularExponent());

        Color lightColor = currentLight.getColorFromPoint(positionOnSurface);
        Color specularColor = material.getSpecularColor();

        return lightColor.componentWiseMultiply(specularColor.multiplyNew(specularCoefficient * viewingAngleDependentIntensityFactor));
    }

    private Color computeDiffuseComponent() {
        double diffuseCoefficient = material.getDiffuseCoefficient();
        double angleDependentIntensityFactor = Math.max(0.0, normal.dot(currentLight.getDirectionToLight(positionOnSurface))); // if it's less than 0, then we don't need any diffuse contribution
        Color lightColor = currentLight.getColorFromPoint(positionOnSurface);
        return lightColor.componentWiseMultiply(material.getDiffuseColor().multiplyNew(diffuseCoefficient * angleDependentIntensityFactor));
    }

    public Color computeColor() {
        Color resultantColor = new Color(0.0, 0.0, 0.0);
        for (Light light : lights) {
            currentLight = light;

            Color specularComponent = computeSpecularComponent();
            assert specularComponent.colorIsValid();
            resultantColor.add(specularComponent);

            Color diffuseComponent = computeDiffuseComponent();
            assert diffuseComponent.colorIsValid();
            resultantColor.add(diffuseComponent);
        }
        resultantColor.add(computeAmbientComponent());
        assert resultantColor.colorIsValid();
        return resultantColor;
    }
}
