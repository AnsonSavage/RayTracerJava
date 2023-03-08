import algorithm.RayTracer;
import algorithm.RenderSettings;
import algorithm.SimpleRayTracer;
import output.ImageOutputter;
import output.PPMOutputter;
import world.World;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        World world = WorldCreator.createPurpleSphereWorld();
        int imageWidth = 500;
        int imageHeight = 500;
        double aspectRatio = (double) imageWidth / imageHeight;

        RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 2, 1);
        RayTracer simpleRecursiveRayTracer = new SimpleRayTracer(settings, world);
        simpleRecursiveRayTracer.render();

        ImageOutputter imageOutputter = new PPMOutputter();

        try {
            imageOutputter.outputImage(simpleRecursiveRayTracer.getImage(), "purple.ppm");
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }
}