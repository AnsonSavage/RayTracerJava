package algorithm;

public class RenderSettings {
    private int maxBounces;
    private int imageWidth;
    private int imageHeight;
    private int squareSamplesPerPixel;

    public RenderSettings(int imageWidth, int imageHeight, int maxBounces, int squareSamplesPerPixel) {
        this.maxBounces = maxBounces;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.squareSamplesPerPixel = squareSamplesPerPixel;
    }

    public int getMaxBounces() {
        return maxBounces;
    }

    public int getSquareSamplesPerPixel() {
        return squareSamplesPerPixel;
    }

    public int getResolutionX() {
        return imageWidth;
    }

    public int getResolutionY() {
        return imageHeight;
    }
}
