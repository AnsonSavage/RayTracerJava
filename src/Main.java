import algorithm.RayTracer;
import algorithm.RenderSettings;
import algorithm.MultiSampleRayTracer;
import output.ImageOutputter;
import output.PPMOutputter;
import world.World;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Create multple refractive images with different iors
        // Iors to test: 1.0, 1.01, 1.1, 1.3, 1.5, 2.0
        int imageWidth = 500;
        int imageHeight = imageWidth;
        double aspectRatio = (double) imageWidth / imageHeight;

        for (double ior = 1.0; ior <= 2.0; ior += 0.05) {
            World world = WorldCreator.createRefractivityTest(ior);
            RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 8, 2);

            RayTracer multiSampleRayTracer = new MultiSampleRayTracer(settings, world);
            multiSampleRayTracer.render();
            try {
                ImageOutputter imageOutputter = new PPMOutputter();
                // Set the file name to be the current time in seconds
                imageOutputter.outputImage(multiSampleRayTracer.getImage(), "output:" + System.currentTimeMillis() / 1000 + ".ppm");
            } catch (IOException e) {
                System.out.println("Could not write to file");
            }
        }

    }
}