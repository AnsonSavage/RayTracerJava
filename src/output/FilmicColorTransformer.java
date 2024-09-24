package output;

import utilities.Color;
import utilities.image.Image;

public class FilmicColorTransformer extends ColorTransformer{

    public FilmicColorTransformer(Image image) {
        super(image);
    }

    private static double applyCurve(double x) {
        // Filmic curve constants
        final double A = 0.22;
        final double B = 0.30;
        final double C = 0.10;
        final double D = 0.20;
        final double E = 0.01;
        final double F = 0.30;
        return ((x * (A * x + C * B) + D * E) / (x * (A * x + B) + D * F)) - E / F;
    }
    @Override
    public Color transformPixel(Color color) {
        Color result = new Color(
                applyCurve(color.getRDouble()),
                applyCurve(color.getGDouble()),
                applyCurve(color.getBDouble())

        );

        result.clamp();
        return result;
    }
}
