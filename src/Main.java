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
        int imageWidth = 100;
        int imageHeight = imageWidth;

        World world = WorldCreator.createHDRIWorldWithFStop(new NaiveIntersectionTester(), 3.6, 3.2);

        RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 7, 7, 2, 2, 2);

        RayTracer multiSamplePathTracer = new MultiSamplePathTracer(settings, world, true);
        multiSamplePathTracer.render();
        try {
            ImageOutputter imageOutputter = new PPMOutputter();

            Image pathTracerOutputImage = multiSamplePathTracer.getImage();
            Image transformedImage = (new FilmicColorTransformer(pathTracerOutputImage)).transform();
            imageOutputter.outputImage(transformedImage, "output:" + System.currentTimeMillis() / 1000 + ".ppm"); // Set the file name to be the current time in seconds
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }
}
