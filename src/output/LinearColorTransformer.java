package output;

import utilities.Color;
import utilities.image.Image;

public class LinearColorTransformer extends ColorTransformer {
    public LinearColorTransformer(Image image) {
        super(image);
    }

    @Override
    public Color transformPixel(Color color) {
        Color clampedColor = color.multiplyNew(1.0);
        clampedColor.clamp();
        return clampedColor;
    }
}
