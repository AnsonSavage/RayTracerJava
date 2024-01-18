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

import java.util.ArrayList;
import java.util.List;

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

        Background background = new ConstantBackground(new Color(0.2, 0.2, 0.2), 0.0);

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

    public static World createScene2World() {
        /**
         * CameraLookAt 0 0 0
         * CameraLookFrom 0 0 1
         * CameraLookUp 0 1 0
         * FieldOfView 90
         *
         * DirectionToLight 1.0 0.0 0.0
         * LightColor 1.0 1.0 1.0
         * AmbientLight 0.1 0.1 0.1
         * BackgroundColor 0.2 0.2 0.2
         *
         *
         * # white sphere
         * Sphere
         *   Center 0.5 0.0 -0.15
         *   Radius 0.05
         *   Kd 0.8
         *   Ks 0.1
         *   Ka 0.3
         *   Od 1.0, 1.0, 1.0
         *   Os 1.0, 1.0, 1.0
         *   Kgls 4.0
         *   Refl 0.0
         *
         * # red sphere
         * Sphere
         *   Center 0.3 0.0 -0.1
         *   Radius 0.08
         *   Kd 0.8
         *   Ks 0.8
         *   Ka 0.1
         *   Od 1.0 0.0 0.0
         *   Os 0.5 1.0 0.5
         *   Kgls 32.0
         *   Refl 0.0
         *
         * # green sphere
         * Sphere
         *   Center -0.6 0.0 0.0
         *   Radius .3
         *   Kd 0.7
         *   Ks 0.5
         *   Ka 0.1
         *   Od 0.0 1.0 0.0
         *   Os 0.5 1.0 0.5
         *   Kgls 64.0
         *   Refl 0.0
         *
         * # reflective sphere
         * Sphere
         *   Center 0.1 -0.55 0.25
         *   Radius 0.3
         *   Kd 0.0
         *   Ks 0.1
         *   Ka 0.1
         *   Od 0.75 0.75 0.75
         *   Os 1.0 1.0 1.0
         *   Kgls 10.0
         *   Refl 0.9
         *
         * # blue triangle
         * Triangle
         *   0.3 -0.3 -0.4
         *   0.0 0.3 -0.1
         *   -0.3 -0.3 0.2
         *   Kd 0.9
         *   Ks 0.9
         *   Ka 0.1
         *   Od 0.0 0.0 1.0
         *   Os 1.0 1.0 1.0
         *   Kgls 32.0
         *   Refl 0.0
         *
         * # yellow triangle
         * Triangle
         *   -0.2 0.1 0.1
         *   -0.2 -0.5 0.2
         *   -0.2 0.1 -0.3
         *   Kd 0.9
         *   Ks 0.5
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
                null,
                (new Vector3(1, 0, 0)).multiply(-1),
                1.0,
                new Color(1, 1, 1)
        );

        world.addLight(sunLight);

        Background background = new ConstantBackground(new Color(0.2, 0.2, 0.2), 0.1);
        world.setBackground(background);

        // Create white sphere
        RenderableObject whiteSphere = new Sphere(
                new Vector3(0.5, 0, -0.15),
                new Material(
                        0.3,
                        0.8,
                        0.1,
                        4,
                        0.0,
                        new Color(1, 1, 1),
                        new Color(1, 1, 1)
                    ),
            0.05
        );

        world.addRenderableObject(whiteSphere);

        // Create red sphere
        RenderableObject redSphere = new Sphere(
                new Vector3(0.3, 0, -0.1),
                new Material(
                        0.1,
                        0.8,
                        0.8,
                        32,
                        0.0,
                        new Color(1, 0, 0),
                        new Color(0.5, 1, 0.5)
                    ),
            0.08
        );
        world.addRenderableObject(redSphere);

        // Create green sphere
        RenderableObject greenSphere = new Sphere(
                new Vector3(-0.6, 0, 0),
                new Material(
                        0.1,
                        0.7,
                        0.5,
                        64,
                        0.0,
                        new Color(0, 1, 0),
                        new Color(0.5, 1, 0.5)
                    ),
            0.3
        );

        world.addRenderableObject(greenSphere);

        // Create reflective sphere
        RenderableObject reflectiveSphere = new Sphere(
                new Vector3(0.1, -0.55, 0.25),
                new Material(
                        0.1,
                        0.0,
                        0.1,
                        10,
                        0.9,
                        new Color(0.75, 0.75, 0.75),
                        new Color(1, 1, 1)
                    ),
            0.3
        );

        world.addRenderableObject(reflectiveSphere);

        // Create blue triangle
        RenderableObject blueTriangle = new Triangle(
                new Vector3(0, 0, 0),
                new Material(
                        0.1,
                        0.9,
                        0.9,
                        32,
                        0.0,
                        new Color(0, 0, 1),
                        new Color(1, 1, 1)
                    ),
                new Vector3(0.3, -0.3, -0.4),
                new Vector3(0, 0.3, -0.1),
                new Vector3(-0.3, -0.3, 0.2)
        );

        world.addRenderableObject(blueTriangle);

        // Create yellow triangle
        RenderableObject yellowTriangle = new Triangle(
                new Vector3(0, 0, 0),
                new Material(
                        0.1,
                        0.9,
                        0.5,
                        4,
                        0.0,
                        new Color(1, 1, 0),
                        new Color(1, 1, 1)
                    ),
                new Vector3(-0.2, 0.1, 0.1),
                new Vector3(-0.2, -0.5, 0.2),
                new Vector3(-0.2, 0.1, -0.3)
        );
        world.addRenderableObject(yellowTriangle);

        return world;
    }

    public static World createPlaneShadowTestWorld() {
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

        Background background = new ConstantBackground(new Color(1.0, 0.0, 0.0), 0);

        world.setBackground(background);

        Material reflectiveMaterial = new Material(
                0.1,
                1.0,
                0.0,
                10,
                0.9,
                new Color(0.75, 0.75, 0.75),
                new Color(1, 1, 1)
        );

        double scaleFactor = 6;

        // Create reflective plane:
        RenderableObject reflectivePlaneHalf1 = new Triangle(
                new Vector3(0,0,0),
                reflectiveMaterial,
                new Vector3(1, -1/scaleFactor, 1),
                new Vector3(1, -1/scaleFactor, -1),
                new Vector3(-1, -1/scaleFactor, -1)
        );
        reflectivePlaneHalf1.scale(scaleFactor);
        world.addRenderableObject(reflectivePlaneHalf1);


        RenderableObject reflectivePlaneHalf2 = new Triangle(
                new Vector3(0,0,0),
                reflectiveMaterial,
                new Vector3(1, -1/scaleFactor, 1),
                new Vector3(-1, -1/scaleFactor, -1),
                new Vector3(-1, -1/scaleFactor, 1)
        );
        reflectivePlaneHalf2.scale(scaleFactor);

        world.addRenderableObject(reflectivePlaneHalf2);


        // Create a sphere to cast a shadow
        RenderableObject sphereToCastShadow = new Sphere(
                new Vector3(0, 0, 0),
                new Material(
                        0.1,
                        0.9,
                        0.5,
                        4,
                        0.0,
                        new Color(1, 0, 0),
                        new Color(1, 0, 0)
                    ),
                .5
        );
//        world.addRenderableObject(sphereToCastShadow);

        // Create a plane to cast a shadow at the same place as the sphere

        RenderableObject shadowPlaneHalf1 = new Triangle(
                new Vector3(0,0,0),
                reflectiveMaterial,
                new Vector3(1, 0, 1),
                new Vector3(1, 0, -1),
                new Vector3(-1, 0, -1)
        );
        world.addRenderableObject(shadowPlaneHalf1);


        RenderableObject shadowPlaneHalf2 = new Triangle(
                new Vector3(0,0,0),
                reflectiveMaterial,
                new Vector3(1, 0, 1),
                new Vector3(-1, 0, -1),
                new Vector3(-1, 0, 1)
        );
        world.addRenderableObject(shadowPlaneHalf2);

        return world;
    }

    public static List<Triangle> createCheckerboard(double startX, double endX, double yLevel, double startZ, double endZ, int numDivisionsX, int numDivisionsZ, Material material1, Material material2) {
        List<Triangle> triangles = new ArrayList<>();
        double xStep = (endX - startX) / numDivisionsX;
        double zStep = (endZ - startZ) / numDivisionsZ;
        for (int i = 0; i < numDivisionsX; i++) {
            for (int j = 0; j < numDivisionsZ; j++) {
                double x1 = startX + i * xStep;
                double x2 = startX + (i + 1) * xStep;
                double z1 = startZ + j * zStep;
                double z2 = startZ + (j + 1) * zStep;
                if ((i + j) % 2 == 0) {
                    triangles.add(new Triangle(new Vector3(0,0,0), material1, new Vector3(x1, yLevel, z1), new Vector3(x1, yLevel, z2), new Vector3(x2, yLevel, z2)));
                    triangles.add(new Triangle(new Vector3(0,0,0), material1, new Vector3(x1, yLevel, z1), new Vector3(x2, yLevel, z2), new Vector3(x2, yLevel, z1)));
                } else {
                    triangles.add(new Triangle(new Vector3(0,0,0), material2, new Vector3(x1, yLevel, z1), new Vector3(x1, yLevel, z2), new Vector3(x2, yLevel, z2)));
                    triangles.add(new Triangle(new Vector3(0,0,0), material2, new Vector3(x1, yLevel, z1), new Vector3(x2, yLevel, z2), new Vector3(x2, yLevel, z1)));
                }
            }
        }
        return triangles;
    }
    public static World createMyOwnWorld() {
        return createScene1World();
//        World world = new World();
//        Camera camera = new Camera(
//                new Vector3(0, 0, 2),
//                new Vector3(0, 0, 0),
//                new Vector3(0, 1, 0),
//                90,
//                1,
//                1
//        );
//
//        world.setCamera(camera);
//
//        Light sunLight = new SunLight(
//                null, // sunlight position is ignored?
//                (new Vector3(0, 1, 1)).multiply(-1),
//                1,
//                new Color(1, 1, 1)
//        );
//
//        world.addLight(sunLight);
//
//        Background background = new ConstantBackground(new Color(0.1, 0.2, 0.3), 0);
//
//        world.setBackground(background);
//
//        Material reflectiveMaterial1 = new Material(
//                0.1,
//                1.0,
//                0.0,
//                10,
//                0.1,
//                new Color(0.1, 0.05, 0.05),
//                new Color(1, 1, 1)
//        );
//
//        Material reflectiveMaterial2 = new Material(
//                0.1,
//                1.0,
//                0.0,
//                10,
//                0.9,
//                new Color(0.8, 0.95, 0.9),
//                new Color(1, 1, 1)
//        );
//
//        List<Triangle> checkerboard = createCheckerboard(-5, 5, -1, -5, 5, 10, 10, reflectiveMaterial1, reflectiveMaterial2);
//
//        for (Triangle triangle : checkerboard) {
//            world.addRenderableObject(triangle);
//        }
//
//        Material metal = new Material(
//                0.2,
//                0.2,
//                0.5,
//                .5,
//                1,
//                new Color(.8, .8, .8),
//                new Color(1, 1, 1)
//        );
//
//        RenderableObject sphere = new Sphere(
//                new Vector3(0, 0, 0),
//                metal,
//                0.5
//        );
//        world.addRenderableObject(sphere);
//
//        // Now let's create a spiral of spheres:
//        double radius = .8;
//        double theta = 0;
//        double dTheta = 0.3;
//        double dRadius = -0.01;
//        double y = -.9;
//        double dy = 0.15;
//        while (y < 3 && radius > 0) {
//            double x = radius * Math.cos(theta);
//            double z = radius * Math.sin(theta);
//            RenderableObject sphere2 = new Sphere(
//                    new Vector3(x, y, z),
//                    metal,
//                    Math.abs(1/(y/1.2 + 1) / 15)
//            );
//            world.addRenderableObject(sphere2);
//            theta += dTheta;
//            radius += dRadius;
//            if (dy > 0) {
//                y += dy;
//                dy -= 0.004;
//            } else {
//                y += 0.00001;
//            }
//        }
//
//        return world;
    }
}
