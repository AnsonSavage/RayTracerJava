package utilities;

public class EmissiveMaterial extends Material {
    private Color emissiveColor;

    // Constructor for just emissive color, assuming a default intensity of 1.0
    public EmissiveMaterial(Color emissiveColor) {
        // Assuming super() calls a default Material constructor; adjust as necessary.
        super(0, 0, 0, 0, 0, 0, new Color(), null, 0, 0, 1);
        this.emissiveColor = emissiveColor; // No intensity to apply
    }

    // Constructor for emissive color with specific intensity, without other material properties
    public EmissiveMaterial(Color emissiveColor, double emissiveIntensity) {
        // Assuming super() calls a default Material constructor; adjust as necessary.
        super(0, 0, 0, 0, 0, 0, new Color(), null, 0, 0, 1);
        setEmissiveColor(emissiveColor, emissiveIntensity); // Apply intensity directly
    }

    // Full constructor including both emissive properties and base material properties
    public EmissiveMaterial(double ambientCoefficient, double diffuseCoefficient, double specularCoefficient, double specularExponent, double reflectivity, double reflectiveRoughness, SurfaceColor diffuseSurfaceColor, SurfaceColor specularSurfaceColor, double transmission, double transmissiveRoughness, double indexOfRefraction, Color emissiveColor, double emissiveIntensity) {
        super(ambientCoefficient, diffuseCoefficient, specularCoefficient, specularExponent, reflectivity, reflectiveRoughness, diffuseSurfaceColor, specularSurfaceColor, transmission, transmissiveRoughness, indexOfRefraction);
        setEmissiveColor(emissiveColor, emissiveIntensity); // Apply intensity directly
    }

    // Setter for emissive color and intensity
    public void setEmissiveColor(Color emissiveColor, double emissiveIntensity) {
        // Apply intensity to color and store
        this.emissiveColor = emissiveColor.multiplyNew(emissiveIntensity);
    }

    public void setEmissiveColor(Color emissiveColor) {
        // Apply default intensity of 1.0 to color and store
        setEmissiveColor(emissiveColor, 1.0);
    }

    // Getter for emissive color
    public Color getEmissiveColor() {
        return emissiveColor;
    }
}
