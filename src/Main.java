import algorithm.RenderSettings;
import algorithm.SimpleRayTracer;
import output.ImageOutputter;
import output.PPMOutputter;
import utilities.Vector3;
import world.World;
import world.scene_objects.Camera;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Simple test:
        World world = new World();
        int imageWidth = 2;
        int imageHeight = 2;
        double aspectRatio = (double) imageWidth / imageHeight;

        world.setCamera(new Camera(
                new Vector3(0,0,1),
                new Vector3(0,0,0),
                90,
                aspectRatio,
                1,
                new Vector3(0,1,0)
        ));

        RenderSettings settings = new RenderSettings(imageWidth, imageHeight, 1, 1);
        SimpleRayTracer simpleRayTracer = new SimpleRayTracer(settings, world);
        simpleRayTracer.render();

        ImageOutputter imageOutputter = new PPMOutputter();

        try {
            imageOutputter.outputImage(simpleRayTracer.getImage(), "test.ppm");
        } catch (IOException e) {
            System.out.println("Could not write to file");
        }
    }
}