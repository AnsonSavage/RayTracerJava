package world.scene_objects.light;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.scene_objects.WorldObject;

public abstract class Light extends WorldObject {
    private double intensity;
    private Color color;

    public Light(Vector3 position, Vector3 direction, double intensity, Color color) {
        super(position, direction);
        this.intensity = intensity;
        this.color = color;
    }

    public abstract Ray getRayToLight(Vector3 point);

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
