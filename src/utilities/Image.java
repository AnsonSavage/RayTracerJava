package utilities;

public class Image {
    private final int resolutionX;
    private final int resolutionY;

    private Color[][] pixels;

    public Image(int resolutionX, int resolutionY) {
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.pixels = new Color[resolutionX][resolutionY];
    }

    public Color getColorAtPixel(int x, int y) {
        return pixels[x][y];
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }
}
