package algorithm;

public class RenderSettings {
    private int maxBounces;
    private int imageWidth;
    private int imageHeight;
    private int squareSamplesPerPixel;

    private int areaLightSamples;

    private int refractiveSamples;


    private int reflectiveSamples;

    public RenderSettings(int imageWidth, int imageHeight, int maxBounces, int squareSamplesPerPixel, int areaLightSamples, int refractiveSamples, int reflectiveSamples) {
        this.maxBounces = maxBounces;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.squareSamplesPerPixel = squareSamplesPerPixel;
        this.areaLightSamples = areaLightSamples;
        this.refractiveSamples = refractiveSamples;
        this.reflectiveSamples = reflectiveSamples;
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

    public int getAreaLightSamples() {
        return areaLightSamples;
    }

    public int getRefractiveSamples() {
        return refractiveSamples;
    }

    public int getReflectiveSamples() {
        return reflectiveSamples;
    }
}
