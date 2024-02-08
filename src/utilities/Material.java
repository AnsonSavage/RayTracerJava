package utilities;

public class Material {


    private double ambientCoefficient;
    private double diffuseCoefficient;
    private double specularCoefficient;
    private double specularExponent;
    private double reflectivity;
    private double squaredReflectiveRoughness;
    private Color diffuseColor;
    private Color specularColor;
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
        this.ambientCoefficient = ambientCoefficient;
        this.diffuseCoefficient = diffuseCoefficient;
        this.specularCoefficient = specularCoefficient;
        this.specularExponent = specularExponent;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
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

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public Color getSpecularColor() {
        return specularColor;
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
}