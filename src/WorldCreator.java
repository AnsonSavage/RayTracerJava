import utilities.Color;
import utilities.Material;
import utilities.Vector3;
import world.World;
import world.background.Background;
import world.background.ConstantBackground;
import world.scene_objects.Camera;
import world.scene_objects.light.Light;
import world.scene_objects.light.SunLight;
import world.scene_objects.renderable_objects.RenderableObject;
import world.scene_objects.renderable_objects.Sphere;
import world.scene_objects.renderable_objects.Triangle;

public class WorldCreator {
    public static World createScene1World() {
        /**
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
         * # reflective sphere
         * Sphere
         *   Center 0.0 0.3 -1.0
         *   Radius 0.25
         *   Kd 0.0
         *   Ks 0.1
         *   Ka 0.1
         *   Od 0.75 0.75 0.75
         *   Os 1.0 1.0 1.0
         *   Kgls 10.0
         *   Refl .9
         *
         * # blue triangle
         * Triangle
         *   0.0 -0.7 -0.5
         *   1.0, 0.4, -1.0
         *   0.0, -0.7, -1.5
         *   Kd 0.9
         *   Ks 1.0
         *   Ka 0.1
         *   Od 0.0 0.0 1.0
         *   Os 1.0 1.0 1.0
         *   Kgls 4.0
         *   Refl 0.0
         *
         * # yellow triangle
         * Triangle
         *   0.0, -0.7, -0.5
         *   0.0, -0.7, -1.5
         *   -1.0, 0.4, -1.0
         *   Kd 0.9
         *   Ks 1.0
         *   Ka 0.1
         *   Od 1.0 1.0 0.0
         *   Os 1.0 1.0 1.0
         *   Kgls 4.0
         *   Refl 0.0
         */

        World world = new World();
        Camera camera = new Camera(
                new Vector3(0, 0, 1),
                new Vector3(0, 0, 0),
                new Vector3(0, 1, 0), 90,
                1,
                1
        );

        world.setCamera(camera);

        Light sunLight = new SunLight(
                null, // sunlight position is ignored?
                (new Vector3(0, 1, 0)).multiply(-1),
                1,
                new Color(1, 1, 1)
        );
        world.addLight(sunLight);

        Background background = new ConstantBackground(new Color(0.2, 0.2, 0.2), 0);

        world.setBackground(background);

        // Create reflective sphere:
        RenderableObject reflectiveSphere = new Sphere(
                new Vector3(0, 0.3, -1),
                new Material(
                        0.1,
                        0.0,
                        0.1,
                        10,
                        0.9,
                        new Color(0.75, 0.75, 0.75),
                        new Color(1, 1, 1)
                    ),
            0.25
        );

        world.addRenderableObject(reflectiveSphere);

        // Create blue triangle
        RenderableObject blueTriangle = new Triangle(
                new Vector3(0,0,0),
                new Material(
                        0.1,
                        0.9,
                        1.0,
                        4,
                        0.0,
                        new Color(0, 0, 1),
                        new Color(1, 1, 1)
                    ),
                new Vector3(0, -0.7, -0.5),
                new Vector3(1, 0.4, -1),
                new Vector3(0, -0.7, -1.5)
        );

        world.addRenderableObject(blueTriangle);

        // Create yellow triangle
        RenderableObject yellowTriangle = new Triangle(
                new Vector3(0,0,0),
                new Material(
                        0.1,
                        0.9,
                        1.0,
                        4,
                        0.0,
                        new Color(1, 1, 0),
                        new Color(1, 1, 1)
                    ),
                new Vector3(0, -0.7, -0.5),
                new Vector3(0, -0.7, -1.5),
                new Vector3(-1, 0.4, -1)
        );

        world.addRenderableObject(yellowTriangle);

        return world;
    }
}
