package utilities.image;

import utilities.Color;
import utilities.image.Image;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;

public class HDRImage extends Image {

    public HDRImage(String filePath) {
        super();
        loadHDRImage(filePath);
    }

        private void loadHDRImage(String filePath) {
            try {

//                String pythonScriptPath = "ReadHDRImage.py";
                String outputFilePath =filePath.substring(0, filePath.lastIndexOf('.')) + ".txt";
//
//                String[] cmd = {"python3", pythonScriptPath, filePath, " >> ", outputFilePath};
//
//                // Start the process builder
//                ProcessBuilder processBuilder = new ProcessBuilder(cmd);
//                // Redirect the output of the process to a file
//
////                processBuilder.redirectOutput(new File(outputFilePath));
//
//                Process process = processBuilder.start();
//                process.waitFor();
//
//                // Now read the output from the file
                File file = new File(outputFilePath);
                BufferedReader reader = new BufferedReader(new FileReader(file));

                boolean isFirstLine = true;
                int x = 0, y = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        String[] resolutionValues = line.split(",");
                        this.resolutionX = Integer.parseInt(resolutionValues[0]);
                        this.resolutionY = Integer.parseInt(resolutionValues[1]);
                        this.pixels = new Color[this.resolutionX][this.resolutionY];
                        isFirstLine = false;
                    } else {
                        String[] rgbValues = line.split(",");
                        if (rgbValues.length >= 3) {
                            double red = Double.parseDouble(rgbValues[0]);
                            double green = Double.parseDouble(rgbValues[1]);
                            double blue = Double.parseDouble(rgbValues[2]);
                            this.pixels[x][y] = new Color(red, green, blue);

                            x++;
                            if (x == this.resolutionX) {
                                x = 0;
                                y++;
                            }
                        }
                    }
                }
                reader.close();

                // Optionally, delete the output file if it is no longer needed
//                file.delete();

            } catch (IOException e) { // | InterruptedException e) {
                System.err.println("Failed to load HDR image: " + e.getMessage());
            }
        }
    }
