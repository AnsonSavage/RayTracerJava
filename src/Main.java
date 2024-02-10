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
        int imageWidth = 1000;
        int imageHeight = imageWidth;
        double aspectRatio = (double) imageWidth / imageHeight;

        World world = WorldCreator.createCoolGlossyReflectionScene(new MedianSplitIntersectionTester());
//        World world = WorldCreator.createMyOwnWorld(new MedianSplitIntersectionTester());
//        world.getIntersectionTester().initialize();
//        for (int i = 1; i < 11; i++) {
//            World boundingBoxes = world.generateBoundingBoxWorld(new NaiveIntersectionTester(), i, true);
            RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 5, 3, 5, 1, 2);

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
//    }

}
