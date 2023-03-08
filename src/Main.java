import algorithm.RenderSettings;
import algorithm.RayTracer;
import algorithm.SimpleRecursiveRayTracer;
import output.ImageOutputter;
import output.PPMOutputter;
import world.World;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        World world = WorldCreator.createScene1World();
        int imageWidth = 1000;
        int imageHeight = 1000;
        double aspectRatio = (double) imageWidth / imageHeight;

        RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 1, 1);
        RayTracer simpleRecursiveRayTracer = new SimpleRecursiveRayTracer(settings, world);
        simpleRecursiveRayTracer.render();

        ImageOutputter imageOutputter = new PPMOutputter();

        try {
            imageOutputter.outputImage(simpleRecursiveRayTracer.getImage(), "scene1.ppm");
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }
}