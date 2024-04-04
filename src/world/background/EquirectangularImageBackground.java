package world.background;

import utilities.Color;
import utilities.image.HDRImage;
import utilities.image.Image;
import utilities.Vector3;

public class EquirectangularImageBackground extends Background{
    private Image equirectangularImage;

    public EquirectangularImageBackground(HDRImage equirectangularImage, double ambientIntensity) {
        super(ambientIntensity);
        this.equirectangularImage = equirectangularImage;
    }

    @Override
    public Color getColor(Vector3 rayDirection) {
        // Normalize the ray direction
        Vector3 dir = rayDirection.copy();

        dir.normalize();

        // Convert direction to spherical coordinates
        double phi = Math.atan2(dir.getZ(), dir.getX());
        double theta = Math.acos(dir.getY()); // Because we're doing Y up

        // Fit spherical coordinates to UV coordinates
        double u = fit(phi, -Math.PI, Math.PI, 0, 1);
        double v = fit(theta, 0, Math.PI, 0, 1);

        // Map UV coordinates to pixel coordinates
        int x = (int)(u * equirectangularImage.getResolutionX());
        int y = (int)(v * equirectangularImage.getResolutionY());

        // Ensure we don't exceed the image boundaries
        x = Math.min(x, equirectangularImage.getResolutionX() - 1);
        y = Math.min(y, equirectangularImage.getResolutionY() - 1);

        // Get color from the HDRI/EXR image
        Color color = equirectangularImage.getColorAtPixel(x, y);

        return color.multiplyNew(0.08);
    }

    // Utility function to fit a value from one range to another
    private double fit(double value, double oldMin, double oldMax, double newMin, double newMax) {
        return (value - oldMin) / (oldMax - oldMin) * (newMax - newMin) + newMin;
    }
}