package world.scene_objects.light;

import utilities.Color;
import utilities.Vector3;

public abstract class InverseSquareFalloffLight extends Light {
    public InverseSquareFalloffLight(Vector3 position, double intensity, Color color) {
        super(position, intensity, color);
    }

    @Override
    protected double getFallOff(double distance) {
        return 1.0 / (distance * distance);
    }
}

