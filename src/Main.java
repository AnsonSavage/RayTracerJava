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
        int imageWidth = 750;
        int imageHeight = imageWidth;
        double aspectRatio = (double) imageWidth / imageHeight;
        double refractionIndex = 3.5;

//        for (double ior = 3.5; ior <= 6.0; ior += 0.05) {
        World world = WorldCreator.createAreaLightWorld(new MedianSplitIntersectionTester());
//        World world = WorldCreator.simpleWorldWithThreeSpheres(new MedianSplitIntersectionTester());
//        World world = WorldCreator.createRefractivityTest(3.0, new NaiveIntersectionTester());
//        World boundingBoxWorld = world.generateBoundingBoxWorld(new MedianSplitIntersectionTester());
        RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 6, 5);

        RayTracer multiSampleRayTracer = new MultiSampleRayTracer(settings, world, true);
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
