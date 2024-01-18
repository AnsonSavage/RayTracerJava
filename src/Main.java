import algorithm.RayTracer;
import algorithm.RenderSettings;
import algorithm.MultiSampleRayTracer;
import output.ImageOutputter;
import output.PPMOutputter;
import world.World;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        World world = WorldCreator.createMyOwnWorld();
        int imageWidth = 500;
        int imageHeight = imageWidth;
        double aspectRatio = (double) imageWidth / imageHeight;

        // Watcher test
        imageWidth += 1;
        imageWidth -= 1;

        RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 4, 8);
        RayTracer simpleRecursiveRayTracer = new MultiSampleRayTracer(settings, world);
        simpleRecursiveRayTracer.render();

        ImageOutputter imageOutputter = new PPMOutputter();

        try {
            // Set the file name to be the current time in seconds
            imageOutputter.outputImage(simpleRecursiveRayTracer.getImage(), "output:" + System.currentTimeMillis() / 1000 + ".ppm");
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }
}