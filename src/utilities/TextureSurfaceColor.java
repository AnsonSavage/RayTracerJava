package utilities;

import utilities.image.Image;

public class TextureSurfaceColor extends SurfaceColor {
    Image image;
    double uScale;
    double uOffset;
    double vScale;
    double vOffset;

    // Constructor without scale and offset parameters defaults to no scaling or offset
    public TextureSurfaceColor(Image image) {
        this(image, 1.0, 0.0, 1.0, 0.0); // Default scale is 1 and offset is 0
    }

    // Constructor with scale and offset parameters for both u and v
    public TextureSurfaceColor(Image image, double uScale, double uOffset, double vScale, double vOffset) {
        this.image = image;
        this.uScale = uScale;
        this.uOffset = uOffset;
        this.vScale = vScale;
        this.vOffset = vOffset;
    }

    @Override
    public Color getColor(UVCoordinates uvCoordinates) {
        // Apply scale and offset to u and v
        double u = (uvCoordinates.getU() * uScale + uOffset);
        double v = (uvCoordinates.getV() * vScale + vOffset);

        // Apply wrapping to ensure u and v are within the [0, 1) range
        u = u - Math.floor(u);
        v = v - Math.floor(v);

        // Convert normalized u and v to pixel coordinates in the image
        int x = (int) (u * (image.getResolutionX() - 1));
        int y = (int) (v * (image.getResolutionY() - 1));

        // Fetch and return the color from the image at the calculated pixel coordinates
        return image.getColorAtPixel(x, y);
    }
}
