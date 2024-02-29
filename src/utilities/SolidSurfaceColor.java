package utilities;

public class SolidSurfaceColor extends SurfaceColor {
    private final Color color;

    public SolidSurfaceColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(UVCoordinates uvCoordinates) {
        return color;
    }
}
