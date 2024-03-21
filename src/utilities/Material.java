package utilities;

public class Material {


    private double ambientCoefficient;
    private double diffuseCoefficient;
    private double specularCoefficient;
    private double specularExponent;
    private double reflectivity;
    private double squaredReflectiveRoughness;

    private SurfaceColor diffuseSurfaceColor;
    private SurfaceColor specularSurfaceColor;
    private double transmission;
    private double squaredTransmissiveRoughness;
    private double indexOfRefraction;


    public Material(double ambientCoefficient, double diffuseCoefficient, double specularCoefficient, double specularExponent, double reflectivity, Color diffuseColor, Color specularColor) {
        this(
                ambientCoefficient,
                diffuseCoefficient,
                specularCoefficient,
                specularExponent,
                reflectivity,
                0,
                diffuseColor,
                specularColor,
                0,
                0,
                1
        );
    }

    public Material(double ambientCoefficient, double diffuseCoefficient, double specularCoefficient, double specularExponent, double reflectivity, double reflectiveRoughness, Color diffuseColor, Color specularColor, double transmission, double transmissiveRoughness, double indexOfRefraction) {
        this(
                ambientCoefficient,
                diffuseCoefficient,
                specularCoefficient,
                specularExponent,
                reflectivity,
                reflectiveRoughness,
                new SolidSurfaceColor(diffuseColor),
                new SolidSurfaceColor(specularColor),
                transmission,
                transmissiveRoughness,
                indexOfRefraction
        );
    }

    public Material(double ambientCoefficient, double diffuseCoefficient, double specularCoefficient, double specularExponent, double reflectivity, double reflectiveRoughness, SurfaceColor diffuseSurfaceColor, SurfaceColor specularSurfaceColor, double transmission, double transmissiveRoughness, double indexOfRefraction) {
        this.ambientCoefficient = ambientCoefficient;
        this.diffuseCoefficient = diffuseCoefficient;
        this.specularCoefficient = specularCoefficient;
        this.specularExponent = specularExponent;
        this.diffuseSurfaceColor = diffuseSurfaceColor;
        this.specularSurfaceColor = specularSurfaceColor;
        this.reflectivity = reflectivity;
        this.squaredReflectiveRoughness = reflectiveRoughness * reflectiveRoughness;
        this.transmission = transmission;
        this.squaredTransmissiveRoughness = transmissiveRoughness * transmissiveRoughness;
        this.indexOfRefraction = indexOfRefraction;
    }

    public double getAmbientCoefficient() {
        return ambientCoefficient;
    }

    public double getDiffuseCoefficient() {
        return diffuseCoefficient;
    }

    public double getSpecularCoefficient() {
        return specularCoefficient;
    }

    public double getSpecularExponent() {
        return specularExponent;
    }

    public double getReflectivity() {
        return reflectivity;
    }

    public double getSquaredReflectiveRoughness() {
        return squaredReflectiveRoughness;
    }

    public Color getDiffuseColor(UVCoordinates uvCoordinates) {
        return diffuseSurfaceColor.getColor(uvCoordinates);
    }

    public Color getSpecularColor(UVCoordinates uvCoordinates) {
        return specularSurfaceColor.getColor(uvCoordinates);
    }

    public double getTransmission() {
        return transmission;
    }

    public double getSquaredTransmissiveRoughness() {
        return squaredTransmissiveRoughness;
    }

    public double getIndexOfRefraction() {
        return indexOfRefraction;
    }

    public boolean isTextured() {
        return diffuseSurfaceColor instanceof TextureSurfaceColor || specularSurfaceColor instanceof TextureSurfaceColor;
    }

    public void setSquaredReflectiveRoughness(double v) {
        squaredReflectiveRoughness = v;
    }

    public boolean isTransmissive() {
        return transmission > 0;
    }
}