import algorithm.MultiSamplePathTracer;
import algorithm.RayTracer;
import algorithm.RenderSettings;
import algorithm.MultiSampleRayTracer;
import algorithm.intersection_optimizations.MedianSplitIntersectionTester;
import algorithm.intersection_optimizations.NaiveIntersectionTester;
import output.FilmicColorTransformer;
import output.ImageOutputter;
import output.PPMOutputter;
import utilities.image.Image;
import world.World;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Create multple refractive images with different iors
        // Iors to test: 1.0, 1.01, 1.1, 1.3, 1.5, 2.0
        int imageWidth = 100;
        int imageHeight = imageWidth;
        double aspectRatio = (double) imageWidth / imageHeight;

//        World world = WorldCreator.createRefractivityTest(1.3, new NaiveIntersectionTester());
        World world = WorldCreator.createHDRIWorld(new NaiveIntersectionTester());

        RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 7, 4, 2, 2, 2);

        RayTracer multiSamplePathTracer = new MultiSamplePathTracer(settings, world, false);
        multiSamplePathTracer.render();
        try {
            ImageOutputter imageOutputter = new PPMOutputter();
            // Set the file name to be the current time in seconds
            Image pathTracerOutputImage = multiSamplePathTracer.getImage();
            Image transformedImage = (new FilmicColorTransformer(pathTracerOutputImage)).transform();
            imageOutputter.outputImage(transformedImage, "output:" + System.currentTimeMillis() / 1000 + ".ppm");
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }
}
