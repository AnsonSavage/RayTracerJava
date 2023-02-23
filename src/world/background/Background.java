package world.background;

import utilities.Color;
import utilities.Vector3;

public abstract class Background {
    private double ambientLight;
    public double getAmbientLight() {
        return ambientLight;
    }

    public void setAmbientLight(double ambientLight) {
        this.ambientLight = ambientLight;
    }
    public Background(double ambientLight) {
        this.ambientLight = ambientLight;
    }
    public abstract Color getColor(Vector3 rayDirection);
}
