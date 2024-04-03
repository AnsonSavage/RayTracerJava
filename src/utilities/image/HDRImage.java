package utilities.image;

import utilities.Color;
import utilities.image.Image;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class HDRImage extends Image {

    public HDRImage(String filePath) {
        super();
        loadHDRImage(filePath);
    }

    private void loadHDRImage(String filePath) {
        try {
            String pythonScriptPath = "ReadHDRImage.py";
            String[] cmd = {"python3", pythonScriptPath, filePath};

            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            int x = 0, y = 0;
            boolean isFirstLine = false;
            while ((line = reader.readLine()) != null) {
                // Initialize the pixels array on the first line to set the image dimensions
                if(isFirstLine) {
                    // This example assumes the Python script also outputs the dimensions as the first line
                    String[] resolutionValues = line.split(",");
                    this.resolutionX = Integer.parseInt(resolutionValues[0]);
                    this.resolutionY = Integer.parseInt(resolutionValues[1]);
                    this.pixels = new Color[resolutionX][resolutionY]; // Generate
                    isFirstLine = false;
                    continue; // Skip the rest of the loop to avoid trying to set a pixel color here
                }

                String[] rgbValues = line.split(",");
                if(rgbValues.length >= 3) {
                    float red = Float.parseFloat(rgbValues[0]);
                    float green = Float.parseFloat(rgbValues[1]);
                    float blue = Float.parseFloat(rgbValues[2]);


                    this.pixels[x][y] = new Color(red, green, blue);

                    // Increment x and y appropriately
                    x++;
                    if(x == this.resolutionX) {
                        x = 0;
                        y++;
                    }
                }
            }
            reader.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to load HDR image: " + e.getMessage());
        }
    }
}
