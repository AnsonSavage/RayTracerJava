package algorithm;

public class RenderSettings {
    private int maxBounces;
    private int imageWidth;
    private int imageHeight;
    private int samplesPerPixel;

    public RenderSettings(int imageWidth, int imageHeight, int maxBounces, int samplesPerPixel) {
        this.maxBounces = maxBounces;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.samplesPerPixel = samplesPerPixel;
    }

    public int getMaxBounces() {
        return maxBounces;
    }

    public int getSamplesPerPixel() {
        return samplesPerPixel;
    }

    public int getResolutionX() {
        return imageWidth;
    }

    public int getResolutionY() {
        return imageHeight;
    }
}
