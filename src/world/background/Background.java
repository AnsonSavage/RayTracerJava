package world.background;

import utilities.Color;
import utilities.Vector3;

public interface Background {
    Color getColor(Vector3 rayDirection);
}
