import algorithm.RenderSettings;
import algorithm.SimpleRayTracer;
import utilities.Vector3;
import world.World;
import world.scene_objects.Camera;

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
    }
}