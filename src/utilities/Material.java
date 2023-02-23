package utilities;

public class Material {

    private double ambientCoefficient;
    private double diffuseCoefficient;
    private double specularCoefficient;
    private double specularExponent;


    private Color diffuseColor;
    private Color specularColor;

    public Material(double ambientCoefficient, double diffuseCoefficient, double specularCoefficient, double specularExponent, Color diffuseColor, Color specularColor) {
        this.ambientCoefficient = ambientCoefficient;
        this.diffuseCoefficient = diffuseCoefficient;
        this.specularCoefficient = specularCoefficient;
        this.specularExponent = specularExponent;
        this.diffuseColor = diffuseColor;
        this.specularColor = specularColor;
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
}
