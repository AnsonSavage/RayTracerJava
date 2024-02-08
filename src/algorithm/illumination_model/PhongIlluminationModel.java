package algorithm.illumination_model;

import utilities.Color;
import utilities.Material;
import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.light.AreaLight;
import world.scene_objects.light.Light;

public class PhongIlluminationModel {
    private Material material;
    private Vector3 viewingDirection;
    private Vector3 normal;
    private Vector3 positionOnSurface;

    private World world;
    /**
     *
     * @param material
     * @param viewingDirection
     * @param normal
     * @param positionOnSurface
     * @param world
     */
    public PhongIlluminationModel(Material material, Vector3 viewingDirection, Vector3 normal, Vector3 positionOnSurface, World world) {
        this.material = material;
        this.viewingDirection = viewingDirection;
        this.normal = normal;
        this.positionOnSurface = positionOnSurface;
        this.world = world;
    }

    private Color computeAmbientComponent() {
        // k_a * I_a * O_d
        double ambientCoefficient = material.getAmbientCoefficient();
        double ambientIntensity = this.world.getBackground().getAmbientIntensity();
        Color ambientColor = this.world.getBackground().getColor(viewingDirection);
        return ambientColor.componentWiseMultiply(material.getDiffuseColor().multiplyNew(ambientCoefficient * ambientIntensity)); // Apparently the ambient light is only dependent on the material's diffuse color.
    }

    private Color computeSpecularComponent(Light currentLight, Ray rayToLight) {
        // k_s * I_p * O_s * max(0, (R dot V)^kgls)
        double specularCoefficient = material.getSpecularCoefficient();
        Vector3 reflectedLightDirection = rayToLight.getDirection().reflect(normal);
        double viewingAngleDependentIntensityFactor = Math.pow(Math.max(0.0, viewingDirection.dot(reflectedLightDirection)), material.getSpecularExponent());

        Color lightColor = currentLight.getColorFromPoint(rayToLight);
        Color specularColor = material.getSpecularColor();

        return lightColor.componentWiseMultiply(specularColor.multiplyNew(specularCoefficient * viewingAngleDependentIntensityFactor));
    }

    private Color computeDiffuseComponent(Light currentLight, Ray rayToLight) {
        double diffuseCoefficient = material.getDiffuseCoefficient();
        double angleDependentIntensityFactor = Math.max(0.0, normal.dot(rayToLight.getDirection())); // if it's less than 0, then we don't need any diffuse contribution
        Color lightColor = currentLight.getColorFromPoint(rayToLight);
        return lightColor.componentWiseMultiply(material.getDiffuseColor().multiplyNew(diffuseCoefficient * angleDependentIntensityFactor));
    }

    public Color computeColor() {
        // Initialize resultant color to black
        Color resultantColor = new Color(0.0, 0.0, 0.0);


        for (Light light : this.world.getNonAreaLights()) {
            Ray rayToLight = light.getRayToLight(positionOnSurface);
            rayToLight.offsetFromOrigin(normal);
            if (!world.isRayBlocked(rayToLight)) {
                addLightContribution(resultantColor, light, rayToLight);
            }
        }


        resultantColor.add(computeAreaLightContributions());

        // Add ambient light component
        resultantColor.add(computeAmbientComponent());
        assert resultantColor.colorIsValid();
        return resultantColor;
    }

    private Color computeAreaLightContributions() {
        Color resultantColor = new Color(0.0, 0.0, 0.0);
        for (AreaLight light : this.world.getAreaLights()) {
            Color lightContribution = new Color(0.0, 0.0, 0.0);
            int sampleCount = light.getNumberOfSamples();
            for (int i = 0; i < sampleCount; i++) {
                Ray rayToLight = light.getRayToLight(positionOnSurface); // This is stochastically sampling the light source because it's an area light
                if (!world.isRayBlocked(rayToLight)) {
                    addLightContribution(lightContribution, light, rayToLight);
                }
            }
            if (sampleCount > 0) {
                lightContribution = lightContribution.multiplyNew(1.0 / sampleCount);
            }
            resultantColor.add(lightContribution);
        }
        return resultantColor;
    }

    private void addLightContribution(Color resultantColor, Light light, Ray rayToLight) {
        Color specularComponent = computeSpecularComponent(light, rayToLight);
        assert specularComponent.colorIsValid();
        resultantColor.add(specularComponent);

        Color diffuseComponent = computeDiffuseComponent(light, rayToLight);
        assert diffuseComponent.colorIsValid();
        resultantColor.add(diffuseComponent);
    }
}
