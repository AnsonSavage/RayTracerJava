package utilities;

public class Material {

    private double ambientCoefficient;
    private double diffuseCoefficient;
    private double specularCoefficient;
    private double specularExponent;
    private double reflectivity;
    private Color diffuseColor;
    private Color specularColor;

    private double indexOfRefraction;
    private double transmission;

    public Material(double ambientCoefficient, double diffuseCoefficient, double specularCoefficient, double specularExponent, double reflectivity, Color diffuseColor, Color specularColor) {
        this(ambientCoefficient, diffuseCoefficient, specularCoefficient, specularExponent, reflectivity, diffuseColor, specularColor, 0, 1);
    }
    public Material(double ambientCoefficient, double diffuseCoefficient, double specularCoefficient, double specularExponent, double reflectivity, Color diffuseColor, Color specularColor, double transmission, double indexOfRefraction) {
        this.ambientCoefficient = ambientCoefficient;
        this.diffuseCoefficient = diffuseCoefficient;
        this.specularCoefficient = specularCoefficient;
        this.specularExponent = specularExponent;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
        this.reflectivity = reflectivity;
        this.transmission = transmission;
        this.indexOfRefraction = indexOfRefraction;
    }
    public double getDiffuseCoefficient() {
        return diffuseCoefficient;
    }

    public void setDiffuseCoefficient(double diffuseCoefficient) {
        this.diffuseCoefficient = diffuseCoefficient;
    }

    public double getSpecularCoefficient() {
        return specularCoefficient;
    }

    public void setSpecularCoefficient(double specularCoefficient) {
        this.specularCoefficient = specularCoefficient;
    }

    public double getSpecularExponent() {
        return specularExponent;
    }

    public void setSpecularExponent(double specularExponent) {
        this.specularExponent = specularExponent;
    }

    public Color getDiffuseColor() {
        return diffuseColor;
    }

    public void setDiffuseColor(Color diffuseColor) {
        this.diffuseColor = diffuseColor;
    }

    public Color getSpecularColor() {
        return specularColor;
    }

    public void setSpecularColor(Color specularColor) {
        this.specularColor = specularColor;
    }
    public double getAmbientCoefficient() {
        return ambientCoefficient;
    }

    public void setAmbientCoefficient(double ambientCoefficient) {
        this.ambientCoefficient = ambientCoefficient;
    }
    public double getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(double reflectivity) {
        this.reflectivity = reflectivity;
    }


    public double getIndexOfRefraction() {
        return indexOfRefraction;
    }
    public boolean isRefractive() {
        return transmission > 0;
    }

    public double getTransmission() {
        return transmission;
    }
}
