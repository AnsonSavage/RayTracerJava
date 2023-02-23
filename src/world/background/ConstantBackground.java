package world.background;

import utilities.Color;
import utilities.Vector3;

public class ConstantBackground implements Background {
    private Color color;

    public ConstantBackground(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(Vector3 rayDirection) {
        return color;
    }
}
