package world.background;

import utilities.Color;
import utilities.Vector3;

public class ConstantBackground extends Background {
    private Color color;

    public ConstantBackground(Color color, double ambientLight) {
        super(ambientLight);
        this.color = color;
    }

    @Override
    public Color getColor(Vector3 rayDirection) {
        return color;
    }
}
