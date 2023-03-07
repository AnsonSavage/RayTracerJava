import algorithm.RenderSettings;
import algorithm.SimpleRayTracer;
import output.ImageOutputter;
import output.PPMOutputter;
import world.World;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        World world = null;
        int imageWidth = 500;
        int imageHeight = 500;
        double aspectRatio = (double) imageWidth / imageHeight;

        RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 1, 1);
        SimpleRayTracer simpleRayTracer = new SimpleRayTracer(settings, world);
        simpleRayTracer.render();

        ImageOutputter imageOutputter = new PPMOutputter();

        try {
            imageOutputter.outputImage(simpleRayTracer.getImage(), "redWhiteAndGreen.ppm");
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }
}