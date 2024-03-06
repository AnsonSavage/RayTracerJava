package world.background;

import utilities.Color;
import utilities.Vector3;

public abstract class Background {
    private double ambientIntensity;
    public double getAmbientIntensity() {
        return ambientIntensity;
    }
    public void setAmbientIntensity(double ambientIntensity) {
        this.ambientIntensity = ambientIntensity;
    }
    public Background(double ambientIntensity) {
        this.ambientIntensity = ambientIntensity;
    }
    public abstract Color getColor(Vector3 rayDirection);
}
