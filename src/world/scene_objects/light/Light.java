package world.scene_objects.light;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.scene_objects.WorldObject;

public abstract class Light extends WorldObject {
    private double intensity;
    private Color color;


    public Light(Vector3 position, double intensity, Color color) {
        super(position);
        this.intensity = intensity;
        this.color = color;
    }

    public abstract Ray getRayToLight(Vector3 point);

    public abstract double getDistanceToLight(Vector3 point);
    public Vector3 getDirectionToLight(Vector3 point) {
        return this.getRayToLight(point).getDirection();
    }

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

    public Color getColorFromPoint(Vector3 point) {
        return this.getColor().multiplyNew(this.getIntensity() * this.getFallOff(this.getDistanceToLight(point)));
    }

    protected abstract double getFallOff(double distance);
}
