package algorithm;

public class RenderSettings {
    private int maxBounces;
    private int imageWidth;
    private int imageHeight;
    private int samplesPerPixel;

    public RenderSettings(int maxBounces, int imageWidth, int imageHeight, int samplesPerPixel) {
        this.maxBounces = maxBounces;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.samplesPerPixel = samplesPerPixel;
    }

    public int getMaxBounces() {
        return maxBounces;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getSamplesPerPixel() {
        return samplesPerPixel;
    }
}
