import algorithm.RenderSettings;
import algorithm.SimpleRayTracer;
import output.ImageOutputter;
import output.PPMOutputter;
import utilities.Color;
import utilities.Material;
import utilities.Vector3;
import world.World;
import world.background.Background;
import world.background.ConstantBackground;
import world.scene_objects.Camera;
import world.scene_objects.renderable_objects.Sphere;
import world.scene_objects.light.Light;
import world.scene_objects.light.SunLight;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        World world = createMyOwnWorld(500, 500);
        World world = createPurpleSphereWorld();
//        World world = createWhiteRedAndGreenSphereWorld();
//        World world = createPurpleAndYellowSphereWorld();
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

    private static double zAsFunctionOfXY(double x, double y) {
        return 50 * (1 / (1 + Math.pow(x, 2) + Math.pow(y, 2)));
    }

    private static World createMyOwnWorld(int numSpheresX, int numSpheresY) {
        World world = new World();
        Camera camera = new Camera(
                new Vector3(0, 0, 55), // Camera position
                new Vector3(0, 0, 0), // Camera direction
                90, // Field of view
                1, // Aspect ratio
                1, // Focal length
                new Vector3(0, 1, 0) // Up vector
        );
        world.setCamera(camera);

        // Create a sun light
        Light sunLight = new SunLight(
                new Vector3(0,0,0),
                new Vector3(0,-1.0,-.5),
                1,
                new Color(1,1,1)
        );
        world.addLight(sunLight);

        // TODO: Consider adding another light

        Background background = new ConstantBackground(
                new Color(0.2, 0.2, 0.2),
                0.3
        );
        world.setBackground(background);

        for (int x = -1 * numSpheresX / 2; x < numSpheresX / 2; x++) {
            for (int y = -1 * numSpheresY / 2; y < numSpheresY / 2; y++) {
                double xFloat = 1.0 * x / 5;
                double yFloat = 1.0 * y / 5;
                Sphere sphere = new Sphere(
                        new Vector3(xFloat, zAsFunctionOfXY(xFloat, yFloat), yFloat),
                        // Create a random material
                        new Material(
                                Math.random(),
                                Math.random(),
                                Math.random(),
                                Math.random() * 100,
                                new Color(xFloat + (double) numSpheresX / 2, Math.abs(zAsFunctionOfXY(xFloat, yFloat)) / 50, yFloat + (double) numSpheresY / 2),
                                new Color(Math.random(), Math.random(), Math.random())
                        ),
                        Math.pow(Math.random(), 2.0) / 3
                );
                world.addRenderableObject(sphere);
            }
        }
        return world;
    }
    private static World createPurpleSphereWorld() {
        /** This is the description of the world that it creates:
         * CameraLookAt 0 0 0
         * CameraLookFrom 0 0 1
         * CameraLookUp 0 1 0
         * FieldOfView 90
         *
         * DirectionToLight 0.0 1.0 0.0
         * LightColor 1.0 1.0 1.0
         * AmbientLight 0.0 0.0 0.0
         * BackgroundColor 0.2 0.2 0.2
         *
         * # purple sphere
         * Sphere
         *   Center 0.0 0.0 0.0
         *   Radius .4
         *   Kd 0.7
         *   Ks 0.2
         *   Ka 0.1
         *   Od 1.0 0.0 1.0
         *   Os 1.0 1.0 1.0
         *   Kgls 16.0
         */
        World world = new World();
        Camera camera = new Camera(
                new Vector3(0,0,1),
                new Vector3(0,0,0),
                90,
                1,
                1,
                new Vector3(0,1,0)
        );
        world.setCamera(camera);

        // Create a sun light
        Light sunLight = new SunLight(
                new Vector3(0,0,0),
                new Vector3(0,-1.0,0),
                1,
                new Color(1,1,1)
        );
        world.addLight(sunLight);

        Background background = new ConstantBackground(
                new Color(0.2, 0.2, 0.2),
                0.0
        );
        world.setBackground(background);


        // Create a purple sphere
        Sphere sphere = new Sphere(
                new Vector3(0,0,0),
                new Material(
                        0.1,
                        0.7,
                        0.2,
                        16.0,
                        new Color(1.0, 0.5, 1.0),
                        new Color(1.0, 1.0, 1.0)
                ),
                0.5
        );
        world.addRenderableObject(sphere);

        return world;
    }

    private static World createWhiteRedAndGreenSphereWorld() {
        /**
         * Here is a description of this world:
         * CameraLookAt 0 0 0
         * CameraLookFrom 0 0 1
         * CameraLookUp 0 1 0
         * FieldOfView 90
         *
         * DirectionToLight 1.0 1.0 1.0
         * LightColor 1.0 1.0 1.0
         * AmbientLight 0.1 0.1 0.1
         * BackgroundColor 0.2 0.2 0.2
         *
         *
         * # white sphere
         * Sphere
         *   Center 0.45 0.0 -0.15
         *   Radius 0.15
         *   Kd 0.8
         *   Ks 0.1
         *   Ka 0.3
         *   Od 1.0, 1.0, 1.0
         *   Os 1.0, 1.0, 1.0
         *   Kgls 4.0
         *
         * # red sphere
         * Sphere
         *   Center 0.0 0.0 -0.1
         *   Radius 0.2
         *   Kd 0.6
         *   Ks 0.3
         *   Ka 0.1
         *   Od 1.0 0.0 0.0
         *   Os 1.0 1.0 1.0
         *   Kgls 32.0
         *
         * # green sphere
         * Sphere
         *   Center -0.6 0.0 0.0
         *   Radius .3
         *   Kd 0.7
         *   Ks 0.2
         *   Ka 0.1
         *   Od 0.0 1.0 0.0
         *   Os 0.5 1.0 0.5
         *   Kgls 64.0
         *
         * # blue sphere
         * Sphere
         *   Center 0.0 -10000.5 0.0
         *   Radius 10000.0
         *   Kd 0.9
         *   Ks 0.0
         *   Ka 0.1
         *   Od 0.0 0.0 1.0
         *   Os 1.0 1.0 1.0
         *   Kgls 16.0
         */

        World world = new World();
        Camera camera = new Camera(
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 0),
                90,
                1,
                1,
                new Vector3(0, 1, 0)
        );
        world.setCamera(camera);

        // Create a sun light
        Light sunLight = new SunLight(
                new Vector3(1, 1, 1),
                new Vector3(-1, -1, -1),
                1,
                new Color(1, 1, 1)
        );
        world.addLight(sunLight);

        Background background = new ConstantBackground(
                new Color(0.2, 0.2, 0.2),
                0.1
        );
        world.setBackground(background);

        // Create a white sphere
        Sphere whiteSphere = new Sphere(
                new Vector3(0.45, 0.0, -0.15),
                new Material(
                        0.3,
                        0.8,
                        0.1,
                        4.0,
                        new Color(1.0, 1.0, 1.0),
                        new Color(1.0, 1.0, 1.0)
                ),
                0.15
        );
        world.addRenderableObject(whiteSphere);

        // Create a red sphere
        Sphere redSphere = new Sphere(
                new Vector3(0.0, 0.0, -0.1),
                new Material(
                        0.1,
                        0.6,
                        0.3,
                        32.0,
                        new Color(1.0, 0.0, 0.0),
                        new Color(1.0, 1.0, 1.0)
                ),
                0.2
        );
        world.addRenderableObject(redSphere);

        // Create a green sphere
        Sphere greenSphere = new Sphere(
                new Vector3(-0.6, 0.0, 0.0),
                new Material(
                        0.1,
                        0.7,
                        0.2,
                        64.0,
                        new Color(0.0, 1.0, 0.0),
                        new Color(0.5, 1.0, 0.5)
                ),
                0.3
        );
        world.addRenderableObject(greenSphere);

        // Create a blue sphere
        Sphere blueSphere = new Sphere(
                new Vector3(0.0, -10000.5, 0.0),
                new Material(
                        0.1,
                        0.9,
                        0.0,
                        16.0,
                        new Color(0.0, 0.0, 1.0),
                        new Color(1.0, 1.0, 1.0)
                ),
                10000.0
        );
        world.addRenderableObject(blueSphere);

        return world;
    }

    private static World createPurpleAndYellowSphereWorld() {
        World world = createPurpleSphereWorld();
        Sphere sphere = new Sphere(
                new Vector3(0, 0, -5),
                new Material(
                        0.9,
                        0.7,
                        0.2,
                        16.0,
                        new Color(1.0, 0.7, 0.0),
                        new Color(1.0, 1.0, 1.0)
                    ),
            5
            );

        world.addRenderableObject(sphere);

        Background background = new ConstantBackground(
                new Color(0.3, 0.3, 0.3),
                0.4
        );
        world.setBackground(background);
        return world;
    }
}