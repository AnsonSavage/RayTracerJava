import algorithm.MultiSamplePathTracer;
import algorithm.RayTracer;
import algorithm.RenderSettings;
import algorithm.MultiSampleRayTracer;
import algorithm.intersection_optimizations.MedianSplitIntersectionTester;
import algorithm.intersection_optimizations.NaiveIntersectionTester;
import output.ImageOutputter;
import output.PPMOutputter;
import world.World;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Create multple refractive images with different iors
        // Iors to test: 1.0, 1.01, 1.1, 1.3, 1.5, 2.0
        int imageWidth = 600;
        int imageHeight = imageWidth;
        double aspectRatio = (double) imageWidth / imageHeight;

        World world = WorldCreator.createAreaLightWorld(new NaiveIntersectionTester());

        RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 5, 2, 10, 2, 2);

        RayTracer multiSamplePathTracer = new MultiSamplePathTracer(settings, world, false);
        multiSamplePathTracer.render();
        try {
            ImageOutputter imageOutputter = new PPMOutputter();
            // Set the file name to be the current time in seconds
            imageOutputter.outputImage(multiSamplePathTracer.getImage(), "output:" + System.currentTimeMillis() / 1000 + ".ppm");
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }
}
