package output;

import utilities.Color;
import utilities.Image;

import java.io.*;

/**
 * Creates files in the following format:
 * P3
 * # of columns # of rows
 * Maximum color value
 * R G B R G B R G B ... etc.
 */
public class PPMOutputter implements ImageOutputter{

    @Override
    public void outputImage(Image image, String filename) throws IOException {
        // Open a file with the given filename
        OutputStream outputStream = new FileOutputStream(filename);
        PrintWriter writer = new PrintWriter(outputStream); // Print writers are nifty because they can be printed to like System.out

        writer.println("P3"); // Specify ASCII RGB as opposed to P6, which is binary
        writer.println(image.getResolutionX() + " " + image.getResolutionY()); // Specify number of columns and rows

        // Specify the maximum color value
        writer.println(255);

        for (int y = 0; y < image.getResolutionY(); y++) {
            for (int x = 0; x < image.getResolutionX(); x++) {
                // Write the RGB values for each pixel
                Color colorAtPixel = image.getColorAtPixel(x, y);
                colorAtPixel.clamp();
                assert colorAtPixel.getRInt() >= 0 && colorAtPixel.getRInt() <= 255;
                assert colorAtPixel.getGInt() >= 0 && colorAtPixel.getGInt() <= 255;
                assert colorAtPixel.getBInt() >= 0 && colorAtPixel.getBInt() <= 255;
                writer.print(colorAtPixel.getRInt() + " " + colorAtPixel.getGInt() + " " + colorAtPixel.getBInt() + " ");
            }
        }

        writer.close();
        outputStream.close();
    }
}
