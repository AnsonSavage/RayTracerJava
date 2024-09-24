package utilities.image;

import utilities.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    protected int resolutionX;
    protected int resolutionY;
    protected Color[][] pixels;

    // Existing constructor
    public Image() {
    }

    public Image(int resolutionX, int resolutionY) {
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
        this.pixels = new Color[resolutionX][resolutionY];
    }

    // New constructor to load image from path
    public Image(String filePath) {
        // Read the image file
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            System.out.println("Image not found!");
            return;
        }

        this.resolutionX = image.getWidth();
        this.resolutionY = image.getHeight();
        this.pixels = new Color[resolutionX][resolutionY];

        // Populate pixels array
        for (int x = 0; x < resolutionX; x++) {
            for (int y = 0; y < resolutionY; y++) {
                java.awt.Color awtColor = new java.awt.Color(image.getRGB(x, y));
                // Convert java.awt.Color to custom Color class
                float red = awtColor.getRed() / 255f;
                float green = awtColor.getGreen() / 255f;
                float blue = awtColor.getBlue() / 255f;
                this.pixels[x][y] = new Color(red, green, blue);
            }
        }
    }

    // Existing methods...
    public Color getColorAtPixel(int x, int y) {
        return pixels[x][y];
    }

    public int getResolutionX() {
        return resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }

    public void setPixel(int x, int y, Color color) {
        pixels[x][y] = color;
    }
}
