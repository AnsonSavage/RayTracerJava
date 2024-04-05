import algorithm.intersection_optimizations.IntersectionTester;
import utilities.*;
import utilities.image.HDRImage;
import utilities.image.Image;
import world.World;
import world.background.Background;
import world.background.ConstantBackground;
import world.background.EquirectangularImageBackground;
import world.scene_objects.Camera;
import world.scene_objects.light.AreaLight;
import world.scene_objects.light.Light;
import world.scene_objects.light.PointLight;
import world.scene_objects.light.SunLight;
import world.scene_objects.renderable_objects.AxisAlignedRectangularPrism;
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
                 * Center 0.0 0.3 -1.0
                 * Radius 0.25
                 * Kd 0.0
                 * Ks 0.1
                 * Ka 0.1
                 * Od 0.75 0.75 0.75
                 * Os 1.0 1.0 1.0
                 * Kgls 10.0
                 * Refl .9
                 *
                 * # blue triangle
                 * Triangle
                 * 0.0 -0.7 -0.5
                 * 1.0, 0.4, -1.0
                 * 0.0, -0.7, -1.5
                 * Kd 0.9
                 * Ks 1.0
                 * Ka 0.1
                 * Od 0.0 0.0 1.0
                 * Os 1.0 1.0 1.0
                 * Kgls 4.0
                 * Refl 0.0
                 *
                 * # yellow triangle
                 * Triangle
                 * 0.0, -0.7, -0.5
                 * 0.0, -0.7, -1.5
                 * -1.0, 0.4, -1.0
                 * Kd 0.9
                 * Ks 1.0
                 * Ka 0.1
                 * Od 1.0 1.0 0.0
                 * Os 1.0 1.0 1.0
                 * Kgls 4.0
                 * Refl 0.0
                 */

                World world = new World();
                Camera camera = new Camera(
                                new Vector3(0, 0, 1),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0), 90,
                                1,
                                1);

                world.setCamera(camera);

                Light sunLight = new SunLight(
                                null, // sunlight position is ignored?
                                (new Vector3(0, 1, 0)).multiply(-1),
                                1,
                                new Color(1, 1, 1));
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
                                                new Color(1, 1, 1)),
                                0.25);

                world.addRenderableObject(reflectiveSphere);

                // Create blue triangle
                RenderableObject blueTriangle = new Triangle(
                                new Vector3(0, 0, 0),
                                new Material(
                                                0.1,
                                                0.9,
                                                1.0,
                                                4,
                                                0.0,
                                                new Color(0, 0, 1),
                                                new Color(1, 1, 1)),
                                new Vector3(0, -0.7, -0.5),
                                new Vector3(1, 0.4, -1),
                                new Vector3(0, -0.7, -1.5));

                world.addRenderableObject(blueTriangle);

                // Create yellow triangle
                RenderableObject yellowTriangle = new Triangle(
                                new Vector3(0, 0, 0),
                                new Material(
                                                0.1,
                                                0.9,
                                                1.0,
                                                4,
                                                0.0,
                                                new Color(1, 1, 0),
                                                new Color(1, 1, 1)),
                                new Vector3(0, -0.7, -0.5),
                                new Vector3(0, -0.7, -1.5),
                                new Vector3(-1, 0.4, -1));

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
                 * Center 0.5 0.0 -0.15
                 * Radius 0.05
                 * Kd 0.8
                 * Ks 0.1
                 * Ka 0.3
                 * Od 1.0, 1.0, 1.0
                 * Os 1.0, 1.0, 1.0
                 * Kgls 4.0
                 * Refl 0.0
                 *
                 * # red sphere
                 * Sphere
                 * Center 0.3 0.0 -0.1
                 * Radius 0.08
                 * Kd 0.8
                 * Ks 0.8
                 * Ka 0.1
                 * Od 1.0 0.0 0.0
                 * Os 0.5 1.0 0.5
                 * Kgls 32.0
                 * Refl 0.0
                 *
                 * # green sphere
                 * Sphere
                 * Center -0.6 0.0 0.0
                 * Radius .3
                 * Kd 0.7
                 * Ks 0.5
                 * Ka 0.1
                 * Od 0.0 1.0 0.0
                 * Os 0.5 1.0 0.5
                 * Kgls 64.0
                 * Refl 0.0
                 *
                 * # reflective sphere
                 * Sphere
                 * Center 0.1 -0.55 0.25
                 * Radius 0.3
                 * Kd 0.0
                 * Ks 0.1
                 * Ka 0.1
                 * Od 0.75 0.75 0.75
                 * Os 1.0 1.0 1.0
                 * Kgls 10.0
                 * Refl 0.9
                 *
                 * # blue triangle
                 * Triangle
                 * 0.3 -0.3 -0.4
                 * 0.0 0.3 -0.1
                 * -0.3 -0.3 0.2
                 * Kd 0.9
                 * Ks 0.9
                 * Ka 0.1
                 * Od 0.0 0.0 1.0
                 * Os 1.0 1.0 1.0
                 * Kgls 32.0
                 * Refl 0.0
                 *
                 * # yellow triangle
                 * Triangle
                 * -0.2 0.1 0.1
                 * -0.2 -0.5 0.2
                 * -0.2 0.1 -0.3
                 * Kd 0.9
                 * Ks 0.5
                 * Ka 0.1
                 * Od 1.0 1.0 0.0
                 * Os 1.0 1.0 1.0
                 * Kgls 4.0
                 * Refl 0.0
                 */

                World world = new World();

                Camera camera = new Camera(
                                new Vector3(0, 0, 1),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0), 90,
                                1,
                                1);

                world.setCamera(camera);

                Light sunLight = new SunLight(
                                null,
                                (new Vector3(1, 0, 0)).multiply(-1),
                                1.0,
                                new Color(1, 1, 1));

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
                                                new Color(1, 1, 1)),
                                0.05);

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
                                                new Color(0.5, 1, 0.5)),
                                0.08);
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
                                                new Color(0.5, 1, 0.5)),
                                0.3);

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
                                                new Color(1, 1, 1)),
                                0.3);

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
                                                new Color(1, 1, 1)),
                                new Vector3(0.3, -0.3, -0.4),
                                new Vector3(0, 0.3, -0.1),
                                new Vector3(-0.3, -0.3, 0.2));

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
                                                new Color(1, 1, 1)),
                                new Vector3(-0.2, 0.1, 0.1),
                                new Vector3(-0.2, -0.5, 0.2),
                                new Vector3(-0.2, 0.1, -0.3));
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
                                1);

                world.setCamera(camera);

                Light sunLight = new SunLight(
                                null,
                                (new Vector3(0, 1, 0)).multiply(-1),
                                1,
                                new Color(1, 1, 1));

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
                                new Color(1, 1, 1));

                double scaleFactor = 6;

                // Create reflective plane:
                RenderableObject reflectivePlaneHalf1 = new Triangle(
                                new Vector3(0, 0, 0),
                                reflectiveMaterial,
                                new Vector3(1, -1 / scaleFactor, 1),
                                new Vector3(1, -1 / scaleFactor, -1),
                                new Vector3(-1, -1 / scaleFactor, -1));
                reflectivePlaneHalf1.scale(scaleFactor);
                world.addRenderableObject(reflectivePlaneHalf1);

                RenderableObject reflectivePlaneHalf2 = new Triangle(
                                new Vector3(0, 0, 0),
                                reflectiveMaterial,
                                new Vector3(1, -1 / scaleFactor, 1),
                                new Vector3(-1, -1 / scaleFactor, -1),
                                new Vector3(-1, -1 / scaleFactor, 1));
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
                                                new Color(1, 0, 0)),
                                .5);
                // world.addRenderableObject(sphereToCastShadow);

                // Create a plane to cast a shadow at the same place as the sphere

                RenderableObject shadowPlaneHalf1 = new Triangle(
                                new Vector3(0, 0, 0),
                                reflectiveMaterial,
                                new Vector3(1, 0, 1),
                                new Vector3(1, 0, -1),
                                new Vector3(-1, 0, -1));
                world.addRenderableObject(shadowPlaneHalf1);

                RenderableObject shadowPlaneHalf2 = new Triangle(
                                new Vector3(0, 0, 0),
                                reflectiveMaterial,
                                new Vector3(1, 0, 1),
                                new Vector3(-1, 0, -1),
                                new Vector3(-1, 0, 1));
                world.addRenderableObject(shadowPlaneHalf2);

                return world;
        }

        public static List<Triangle> createCheckerboardFromTriangles(double startX, double endX, double yLevel,
                        double startZ, double endZ, int numDivisionsX, int numDivisionsZ, Material material1,
                        Material material2) {
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
                                        triangles.add(new Triangle(new Vector3(0, 0, 0), material1,
                                                        new Vector3(x1, yLevel, z1), new Vector3(x1, yLevel, z2),
                                                        new Vector3(x2, yLevel, z2)));
                                        triangles.add(new Triangle(new Vector3(0, 0, 0), material1,
                                                        new Vector3(x1, yLevel, z1), new Vector3(x2, yLevel, z2),
                                                        new Vector3(x2, yLevel, z1)));
                                } else {
                                        triangles.add(new Triangle(new Vector3(0, 0, 0), material2,
                                                        new Vector3(x1, yLevel, z1), new Vector3(x1, yLevel, z2),
                                                        new Vector3(x2, yLevel, z2)));
                                        triangles.add(new Triangle(new Vector3(0, 0, 0), material2,
                                                        new Vector3(x1, yLevel, z1), new Vector3(x2, yLevel, z2),
                                                        new Vector3(x2, yLevel, z1)));
                                }
                        }
                }
                return triangles;
        }

        public static List<AxisAlignedRectangularPrism> createCheckerboardFromAxisAlignedRectangularPrisms(
                        double startX, double endX, double yLevel, double startZ, double endZ, int numDivisionsX,
                        int numDivisionsZ, Material material1, Material material2) {
                List<AxisAlignedRectangularPrism> prisms = new ArrayList<>();

                double tileSizeX = (endX - startX) / numDivisionsX;
                double tileSizeZ = (endZ - startZ) / numDivisionsZ;
                double thickness = 0.0; // Define the thickness of each tile; adjust as necessary

                for (int x = 0; x < numDivisionsX; x++) {
                        for (int z = 0; z < numDivisionsZ; z++) {
                                // Alternate materials based on the current tile's position
                                Material currentMaterial = ((x + z) % 2 == 0) ? material1 : material2;

                                // Calculate the center position of the current tile
                                double centerX = startX + x * tileSizeX + tileSizeX / 2.0;
                                double centerZ = startZ + z * tileSizeZ + tileSizeZ / 2.0;

                                // Create a rectangular prism for the tile
                                Vector3 position = new Vector3(centerX, yLevel + thickness / 2.0, centerZ);
                                Vector3 dimensions = new Vector3(tileSizeX, thickness, tileSizeZ);
                                AxisAlignedRectangularPrism tile = new AxisAlignedRectangularPrism(position,
                                                currentMaterial, dimensions);

                                prisms.add(tile);
                        }
                }

                return prisms;
        }

        public static World createMyOwnWorld(IntersectionTester intersectionTester) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);
                World world = new World(camera, intersectionTester);

                Light sunLight = new SunLight(
                                null, // sunlight position is ignored?
                                (new Vector3(0, 1, 1)).multiply(-1),
                                1,
                                new Color(1, 1, 1));

                world.addLight(sunLight);

                Background background = new ConstantBackground(new Color(0.1, 0.2, 0.3), 0);

                world.setBackground(background);

                Material reflectiveMaterial1 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.1,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1));

                Material reflectiveMaterial2 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.9,
                                new Color(0.8, 0.95, 0.9),
                                new Color(1, 1, 1));

                // List<Triangle> checkerboard = createCheckerboardFromTriangles(-5, 5, -1, -5,
                // 5, 10, 10, reflectiveMaterial1, reflectiveMaterial2);
                List<AxisAlignedRectangularPrism> checkerboard = createCheckerboardFromAxisAlignedRectangularPrisms(-6,
                                6, -1, -5, 5, 10, 10, reflectiveMaterial1, reflectiveMaterial2);

                for (RenderableObject checkerboardPiece : checkerboard) {
                        world.addRenderableObject(checkerboardPiece);
                }

                Material metal = new Material(
                                0.2,
                                0.2,
                                0.5,
                                .5,
                                1,
                                new Color(.8, .8, .8),
                                new Color(1, 1, 1));

                RenderableObject sphere = new Sphere(
                                new Vector3(0, 0, 0),
                                metal,
                                0.5);
                world.addRenderableObject(sphere);

                // Now let's create a spiral of spheres:
                double radius = .8;
                double theta = 0;
                double dTheta = 0.3;
                double dRadius = -0.01;
                double y = -.9;
                double dy = 0.15;
                while (y < 3 && radius > 0) {
                        double x = radius * Math.cos(theta);
                        double z = radius * Math.sin(theta);
                        RenderableObject sphere2 = new Sphere(
                                        new Vector3(x, y, z),
                                        metal,
                                        Math.abs(1 / (y / 1.2 + 1) / 15));
                        world.addRenderableObject(sphere2);
                        theta += dTheta;
                        radius += dRadius;
                        if (dy > 0) {
                                y += dy;
                                dy -= 0.004;
                        } else {
                                y += 0.00001;
                        }
                }

                return world;
        }

        public static World createRefractivityTest(double refractiveIndex, IntersectionTester intersectionTester) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);

                World world = new World(camera, intersectionTester);

                Light sunLight = new SunLight(
                                null, // sunlight position is ignored?
                                (new Vector3(1, 1, 1)).multiply(-1),
                                1,
                                new Color(1, 1, 1));

                world.addLight(sunLight);

                Background background = new ConstantBackground(new Color(0.1, 0.2, 0.3), 0);

                world.setBackground(background);

                Material reflectiveMaterial1 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.1,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1));

                Material reflectiveMaterial2 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.9,
                                new Color(0.8, 0.95, 0.9),
                                new Color(1, 1, 1));

                List<AxisAlignedRectangularPrism> checkerboard = createCheckerboardFromAxisAlignedRectangularPrisms(-5,
                                5, -1, -5, 5, 10, 10, reflectiveMaterial1, reflectiveMaterial2);

                for (AxisAlignedRectangularPrism axisAlignedRectangularPrism : checkerboard) {
                        world.addRenderableObject(axisAlignedRectangularPrism);
                }

                Material refractiveMaterial = new Material(
                                0.0,
                                0.0,
                                0.0,
                                1,
                                0.1,
                                0,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1),
                                1,
                                0,
                                refractiveIndex);

                RenderableObject sphere = new Sphere(
                                new Vector3(0, 0, 0),
                                refractiveMaterial,
                                0.8);
                world.addRenderableObject(sphere);

                // Add a smaller blue sphere directly behind this big sphere
                Material blueMaterial = new Material(
                                0.4,
                                1.0,
                                0.0,
                                1,
                                0.1,
                                new Color(0.1, 0.05, 1),
                                new Color(0.1, 0.1, 0.9));
                RenderableObject blueSphere = new Sphere(
                                new Vector3(0, 0, -1),
                                blueMaterial,
                                0.2);
                world.addRenderableObject(blueSphere);

                // Add a smaller red sphere to the right of the blue sphere
                Material redMaterial = new Material(
                                0.4,
                                1.0,
                                0.0,
                                1,
                                0.1,
                                new Color(0.9, 0.05, 0.05),
                                new Color(0.9, 0.1, 0.1));
                RenderableObject redSphere = new Sphere(
                                new Vector3(0.5, 0, -1),
                                redMaterial,
                                0.2);
                world.addRenderableObject(redSphere);

                // Add a smaller green sphere to the left of the blue sphere
                Material greenMaterial = new Material(
                                0.4,
                                1.0,
                                0.0,
                                1,
                                0.1,
                                new Color(0.1, 0.9, 0.05),
                                new Color(0.1, 0.9, 0.1));
                RenderableObject greenSphere = new Sphere(
                                new Vector3(-0.5, 0, -1),
                                greenMaterial,
                                0.2);
                world.addRenderableObject(greenSphere);

                return world;
        }

        public static World createPointLightTestWorld() {
                World world = new World();
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);

                world.setCamera(camera);

                Light redPointLight = new PointLight(
                                new Vector3(-1, 0.5, 0),
                                2,
                                new Color(1, 0, 0));

                world.addLight(redPointLight);

                Light greenPointLight = new PointLight(
                                new Vector3(0, 0.5, 0),
                                2,
                                new Color(0, 1, 0));
                world.addLight(greenPointLight);

                Light bluePointLight = new PointLight(
                                new Vector3(1, 0.5, 0),
                                2,
                                new Color(0, 0, 1));
                world.addLight(bluePointLight);

                Background background = new ConstantBackground(new Color(0.1, 0.1, 0.1), .9);
                world.setBackground(background);

                // Add some spheres for testing shadows
                Material whiteMaterial = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.1,
                                new Color(0.9, 0.9, 0.9),
                                new Color(1, 1, 1));

                Sphere sphere1 = new Sphere(
                                new Vector3(-0.5, 0, 0),
                                whiteMaterial,
                                0.2);
                world.addRenderableObject(sphere1);

                Sphere sphere2 = new Sphere(
                                new Vector3(0.5, 0, 0),
                                whiteMaterial,
                                0.2);
                world.addRenderableObject(sphere2);

                Material diffuseMaterial1 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.01,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1));

                Material reflectiveMaterial2 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.01,
                                new Color(0.8, 0.95, 0.9),
                                new Color(1, 1, 1));

                List<Triangle> checkerboard = createCheckerboardFromTriangles(-5, 5, -1.5, -5, 5, 10, 10,
                                diffuseMaterial1, reflectiveMaterial2);

                for (Triangle triangle : checkerboard) {
                        world.addRenderableObject(triangle);
                }
                return world;
        }

        public static World simpleWorldWithThreeSpheres(IntersectionTester intersectionTester) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);
                World world = new World(camera, intersectionTester);

                Background background = new ConstantBackground(new Color(0.1, 0.1, 0.1), .9);
                world.setBackground(background);

                Material whiteMaterial = new Material(
                                0.4,
                                1.0,
                                0.0,
                                10,
                                0.1,
                                new Color(0.9, 0.9, 0.9),
                                new Color(1, 1, 1));

                Sphere sphere1 = new Sphere(
                                new Vector3(-0.5, 0, 0),
                                whiteMaterial,
                                0.2);
                world.addRenderableObject(sphere1);

                Sphere sphere2 = new Sphere(
                                new Vector3(0, 0, 0),
                                whiteMaterial,
                                0.2);
                world.addRenderableObject(sphere2);

                Sphere sphere3 = new Sphere(
                                new Vector3(0.5, 0, 0),
                                whiteMaterial,
                                0.2);
                world.addRenderableObject(sphere3);

                // Add a few point lights
                Light redPointLight = new PointLight(
                                new Vector3(-1, 0.5, 0),
                                2,
                                new Color(1, .9, .9));
                world.addLight(redPointLight);

                // Add sun light
                Light sunLight = new SunLight(
                                null,
                                new Vector3(1, 1, 1).multiplyNew(-1), // direction
                                2.0,
                                new Color(1, 1, 1));
                world.addLight(sunLight);

                Material reflectiveMaterial1 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.1,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1));

                Material reflectiveMaterial2 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.9,
                                new Color(0.8, 0.95, 0.9),
                                new Color(1, 1, 1));

                List<Triangle> checkerboard = createCheckerboardFromTriangles(-5, 5, -1, -5, 5, 10, 10,
                                reflectiveMaterial1, reflectiveMaterial2);

                for (Triangle triangle : checkerboard) {
                        world.addRenderableObject(triangle);
                }
                return world;
        }

        public static World createAxisAlignedRectangularPrismWorld(IntersectionTester intersectionTester) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);
                World world = new World(camera, intersectionTester);

                Background background = new ConstantBackground(new Color(0.1, 0.1, 0.1), .9);
                world.setBackground(background);

                Material whiteMaterial = new Material(
                                0.4,
                                1.0,
                                0.0,
                                10,
                                0.1,
                                new Color(0.9, 0.9, 0.9),
                                new Color(1, 1, 1));

                AxisAlignedRectangularPrism prism = new AxisAlignedRectangularPrism(
                                new Vector3(-0.5, -0.5, -0.5),
                                whiteMaterial,
                                new Vector3(0.5, 0.5, 0.5));
                world.addRenderableObject(prism);

                // Add a few point lights
                Light redPointLight = new PointLight(
                                new Vector3(-1, 0.5, 0),
                                2,
                                new Color(1, .9, .9));
                world.addLight(redPointLight);

                // Add sun light
                Light sunLight = new SunLight(
                                null,
                                new Vector3(1, 1, 1).multiplyNew(-1), // direction
                                2.0,
                                new Color(1, 1, 1));
                world.addLight(sunLight);
                return world;
        }

        public static World createAreaLightWorld(IntersectionTester intersectionTester) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);
                World world = new World(camera, intersectionTester);

                Background background = new ConstantBackground(new Color(0.1, 0.1, 0.1), .9);
                world.setBackground(background);

                Material material1 = new Material(
                                0.4,
                                1.0,
                                0.0,
                                10,
                                0.1,
                                new Color(0.9, 0.9, 0.9),
                                new Color(1, 1, 1));

                Material material2 = new Material(
                                0.4,
                                1.0,
                                0.0,
                                10,
                                0.1,
                                new Color(0.1, 0.1, 0.1),
                                new Color(1, 1, 1));

                List<AxisAlignedRectangularPrism> prims = createCheckerboardFromAxisAlignedRectangularPrisms(-5, 5, -1,
                                -5, 5, 10, 10, material1, material2);

                for (AxisAlignedRectangularPrism prim : prims) {
                        world.addRenderableObject(prim);
                }

                // Add a sphere and an area light
                Sphere sphere = new Sphere(
                                new Vector3(0, 0, 0),
                                material1,
                                0.2);

                world.addRenderableObject(sphere);

                AreaLight areaLight = new AreaLight(
                                new Sphere(
                                                new Vector3(2, 2, 2),
                                                null,
                                                0.8),
                                10,
                                new Color(1, 1, 1));
                world.addLight(areaLight);

                return world;
        }

        public static World createRefractivityTestWithRoughness(double refractiveIndex,
                        IntersectionTester intersectionTester, double roughness) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);

                World world = new World(camera, intersectionTester);

                Light sunLight = new SunLight(
                                null, // sunlight position is ignored?
                                (new Vector3(1, 1, 1)).multiply(-1),
                                1,
                                new Color(1, 1, 1));

                world.addLight(sunLight);

                Background background = new ConstantBackground(new Color(0.1, 0.2, 0.3), 0);

                world.setBackground(background);

                Material reflectiveMaterial1 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.1,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1));

                Material reflectiveMaterial2 = new Material(
                                0.1,
                                1.0,
                                0.0,
                                10,
                                0.9,
                                new Color(0.8, 0.95, 0.9),
                                new Color(1, 1, 1));

                List<AxisAlignedRectangularPrism> checkerboard = createCheckerboardFromAxisAlignedRectangularPrisms(-5,
                                5, -1, -5, 5, 10, 10, reflectiveMaterial1, reflectiveMaterial2);

                for (AxisAlignedRectangularPrism axisAlignedRectangularPrism : checkerboard) {
                        world.addRenderableObject(axisAlignedRectangularPrism);
                }

                Material refractiveMaterial = new Material(
                                0.0,
                                0.0,
                                0.0,
                                1,
                                0.1,
                                0,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1),
                                1,
                                roughness,
                                refractiveIndex);

                RenderableObject sphere = new Sphere(
                                new Vector3(0, 0, 0),
                                refractiveMaterial,
                                0.8);
                world.addRenderableObject(sphere);

                // Add a smaller blue sphere directly behind this big sphere
                Material blueMaterial = new Material(
                                0.4,
                                1.0,
                                0.0,
                                1,
                                0.1,
                                new Color(0.1, 0.05, 1),
                                new Color(0.1, 0.1, 0.9));
                RenderableObject blueSphere = new Sphere(
                                new Vector3(0, 0, -1),
                                blueMaterial,
                                0.2);
                world.addRenderableObject(blueSphere);

                // Add a smaller red sphere to the right of the blue sphere
                Material redMaterial = new Material(
                                0.4,
                                1.0,
                                0.0,
                                1,
                                0.1,
                                new Color(0.9, 0.05, 0.05),
                                new Color(0.9, 0.1, 0.1));
                RenderableObject redSphere = new Sphere(
                                new Vector3(0.5, 0, -1),
                                redMaterial,
                                0.2);
                world.addRenderableObject(redSphere);

                // Add a smaller green sphere to the left of the blue sphere
                Material greenMaterial = new Material(
                                0.4,
                                1.0,
                                0.0,
                                1,
                                0.1,
                                new Color(0.1, 0.9, 0.05),
                                new Color(0.1, 0.9, 0.1));
                RenderableObject greenSphere = new Sphere(
                                new Vector3(-0.5, 0, -1),
                                greenMaterial,
                                0.2);
                world.addRenderableObject(greenSphere);

                return world;
        }

        public static World createCoolGlossyReflectionScene(IntersectionTester intersectionTester, double ior) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);

                World world = new World(camera, intersectionTester);

                Background background = new ConstantBackground(new Color(0.1, 0.2, 0.3), 1);
                world.setBackground(background);

                Material darkRoughMaterial = new Material(
                                0.1,
                                0.4,
                                0.6,
                                3,
                                0.5,
                                0.19,
                                (new Color(0.1, 0.1, 0.1)).multiplyNew(2),
                                new Color(0.1, 0.1, 0.1),
                                0,
                                0,
                                0);

                Material darkSmoothMaterial = new Material(
                                0.1,
                                0.4,
                                0.6,
                                3,
                                0.5,
                                0.04,
                                new Color(0.02, 0.02, 0.02),
                                new Color(0.1, 0.1, 0.1),
                                0,
                                0,
                                0);

                List<AxisAlignedRectangularPrism> checkerboard = createCheckerboardFromAxisAlignedRectangularPrisms(-5,
                                5, -1, -5, 5, 10, 10, darkRoughMaterial, darkSmoothMaterial);
                for (AxisAlignedRectangularPrism axisAlignedRectangularPrism : checkerboard) {
                        world.addRenderableObject(axisAlignedRectangularPrism);
                }

                // Add three spheres of red, green, and blue colors, and then a glass sphere in
                // the middle
                Material redMaterial = new Material(
                                0.2,
                                0.9,
                                0.0,
                                1,
                                0.3,
                                0.15,
                                new Color(0.9, 0.05, 0.05),
                                new Color(0.9, 0.1, 0.1),
                                0,
                                0,
                                0);
                Sphere redSphere = new Sphere(
                                new Vector3(1, 0, 0),
                                redMaterial,
                                0.25);
                world.addRenderableObject(redSphere);

                Material greenMaterial = new Material(
                                0.2,
                                .8,
                                0.0,
                                1,
                                0.3,
                                0.2,
                                new Color(0.05, 0.9, 0.05),
                                new Color(0.1, 0.9, 0.1),
                                0,
                                0,
                                0);
                Sphere greenSphere = new Sphere(
                                new Vector3(-1, 0, 0),
                                greenMaterial,
                                0.25);
                world.addRenderableObject(greenSphere);

                Material blueMaterial = new Material(
                                0.2,
                                1.0,
                                0.0,
                                1,
                                0.4,
                                0.3,
                                new Color(0.05, 0.05, 0.9),
                                new Color(0.1, 0.1, 0.9),
                                0,
                                0,
                                0);
                Sphere blueSphere = new Sphere(
                                new Vector3(0, 0, -1),
                                blueMaterial,
                                0.25);
                world.addRenderableObject(blueSphere);

                Material glassMaterial = new Material(
                                0.0,
                                0.0,
                                0.0,
                                1,
                                0.1,
                                0,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1),
                                1,
                                0.09,
                                ior);
                Sphere glassSphere = new Sphere(
                                new Vector3(0, 0, -.4),
                                glassMaterial,
                                1.1);
                world.addRenderableObject(glassSphere);

                // Add a slightly yellow spherical area light
                AreaLight areaLight = new AreaLight(
                                new Sphere(
                                                new Vector3(2, 5, 2),
                                                null,
                                                0.8),
                                100,
                                new Color(1, 1, 0.9));
                world.addLight(areaLight);
                return world;
        }

        public static World createTextureWorld(IntersectionTester intersectionTester) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);

                World world = new World(camera, intersectionTester);

                Background background = new ConstantBackground(new Color(0.1, 0.2, 0.3), 1);
                world.setBackground(background);

                AxisAlignedRectangularPrism floor = new AxisAlignedRectangularPrism(
                                new Vector3(0, -1, 0),
                                new Material(
                                                0.1,
                                                0.4,
                                                0.6,
                                                3,
                                                0.5,
                                                0.19,
                                                new TextureSurfaceColor(new Image(
                                                                "/home/anson/Documents/CS_455/Ray_Tracer_Java/RayTracerJava/wood_floor_1k/textures/wood_floor_diff_1k.jpg")),
                                                new SolidSurfaceColor(new Color(0.1, 0.1, 0.1)),
                                                0,
                                                0,
                                                0),
                                new Vector3(10, 0.01, 10));
                world.addRenderableObject(floor);

                // Add three spheres of red, green, and blue colors, and then a glass sphere in
                // the middle
                Material glassMaterial = new Material(
                                0.0,
                                0.0,
                                0.0,
                                1,
                                0.1,
                                0,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1),
                                1,
                                0.09,
                                1.3);
                // Material redMaterial = new Material(
                // 0.2,
                // 0.9,
                // 0.0,
                // 1,
                // 0.3,
                // 0.15,
                // new Color(0.9, 0.05, 0.05),
                // new Color(0.9, 0.1, 0.1),
                // 0,
                // 0,
                // 0
                // );
                Sphere redSphere = new Sphere(
                                new Vector3(1, 0, 0),
                                glassMaterial,
                                0.25);
                world.addRenderableObject(redSphere);

                Material greenMaterial = new Material(
                                0.2,
                                0.4,
                                0.4,
                                1,
                                0.3,
                                0.05,
                                new Color(0.05, 0.9, 0.05),
                                new Color(0.1, 0.9, 0.1),
                                0,
                                0,
                                0);
                Sphere greenSphere = new Sphere(
                                new Vector3(-1, 0, 0),
                                greenMaterial,
                                0.25);
                world.addRenderableObject(greenSphere);

                Material blueMaterial = new Material(
                                0.2,
                                1.0,
                                0.0,
                                1,
                                0.4,
                                0.3,
                                new Color(0.05, 0.05, 0.9),
                                new Color(0.1, 0.1, 0.9),
                                0,
                                0,
                                0);
                Sphere blueSphere = new Sphere(
                                new Vector3(0, 0, -1),
                                blueMaterial,
                                0.25);
                world.addRenderableObject(blueSphere);

                Material parrisMaterial = new Material(

                                0.1,
                                0.8,
                                0.1,
                                3,
                                0.2,
                                0.04,
                                new TextureSurfaceColor(
                                                new Image("/home/anson/Documents/CS_455/Ray_Tracer_Java/RayTracerJava/textures/Parris.jpg"),
                                                3.0,
                                                0.0,
                                                3.0,
                                                0.0),
                                new SolidSurfaceColor(new Color(0.1, 0.1, 0.1)),
                                0,
                                0,
                                0);
                Sphere glassSphere = new Sphere(
                                new Vector3(0, 0, -.4),
                                parrisMaterial,
                                1.1);
                world.addRenderableObject(glassSphere);

                // Add a slightly yellow spherical area light
                AreaLight areaLight = new AreaLight(
                                new Sphere(
                                                new Vector3(2, 5, 2),
                                                null,
                                                0.8),
                                100,
                                new Color(1, 1, 0.9));
                world.addLight(areaLight);
                return world;
        }

        public static World createCornellBoxWorld(IntersectionTester intersectionTester) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);

                World world = new World(camera, intersectionTester);

                Background background = new ConstantBackground(new Color(0.4, 0.6, 0.6), 0); // Since this is used for
                                                                                             // path tracing, I'm
                                                                                             // turning ambient light
                                                                                             // off for now
                world.setBackground(background);

                // Create white floor
                Material whiteWall = new Material(
                                0.0,
                                1.0,
                                0.0,
                                1,
                                0.1,
                                0.8,
                                new Color(0.9, 0.9, 0.9),
                                new Color(1, 1, 1),
                                0,
                                0,
                                0);
                // Floor quad
                world.addRenderableObjects(makeQuad(new Vector3(-1, -1, -1), new Vector3(-1, -1, 1),
                                new Vector3(1, -1, 1), new Vector3(1, -1, -1), whiteWall));

                // Ceiling quad - vertices flipped to correct winding order
                // Ceiling quad - correct the winding order if needed
                world.addRenderableObjects(makeQuad(new Vector3(-1, 1, 1), new Vector3(-1, 1, -1),
                                new Vector3(1, 1, -1), new Vector3(1, 1, 1), whiteWall));

                // Back wall quad - vertices flipped to correct winding order
                world.addRenderableObjects(makeQuad(new Vector3(-1, 1, -1), new Vector3(-1, -1, -1),
                                new Vector3(1, -1, -1), new Vector3(1, 1, -1), whiteWall));

                Material greenWall = new Material(
                                0.0,
                                1.0,
                                0.0,
                                1,
                                0.1,
                                0.8,
                                new Color(0.1, 0.8, 0.1),
                                new Color(0.5, 1, 0.5),
                                0,
                                0,
                                0);

                Material redWall = new Material(
                                0.0,
                                1.0,
                                0.0,
                                1,
                                0.1,
                                0.8,
                                new Color(0.8, 0.1, 0.1),
                                new Color(1, 0.5, 0.5),
                                0,
                                0,
                                0);

                // Left wall quad - vertices flipped to correct winding order
                world.addRenderableObjects(makeQuad(new Vector3(-1, 1, -1), new Vector3(-1, 1, 1),
                                new Vector3(-1, -1, 1), new Vector3(-1, -1, -1), redWall));

                // Right wall quad - vertices flipped to correct winding order
                world.addRenderableObjects(makeQuad(new Vector3(1, 1, 1), new Vector3(1, 1, -1),
                                new Vector3(1, -1, -1), new Vector3(1, -1, 1), greenWall));

//                // Add area light
                AreaLight areaLight = new AreaLight(
                                new AxisAlignedRectangularPrism(
                                                new Vector3(0, 0.95, 0),
                                                null,
                                                new Vector3(0.3, 0.005, 0.3)),
                                6,
                                new Color(1, 1, 1));
                world.addLight(areaLight);

//                RenderableObject areaLight = new AxisAlignedRectangularPrism(
//                                                new Vector3(0, 0.95, 0),
//                                                new EmissiveMaterial(new Color(1, 1, 1), 10),
//                                                new Vector3(0.3, 0.005, 0.3)
//                );
//                world.addRenderableObject(areaLight);

                // Add a glass sphere
                Material glass = new Material(
                                0.0,
                                0.0,
                                0.0,
                                1,
                                0.1,
                                0,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1),
                                1,
                                0,
                                1.6);

                Sphere sphere = new Sphere(
                                new Vector3(-0.5, -0.6, 0.2),
                                glass,
                                0.4);

                world.addRenderableObject(sphere);

                Vector3 boxCenter = new Vector3(0.45, -0.8, 0);
                Vector3 boxDimensions = new Vector3(0.52, 1.6, 0.52); // width, height, and depth of the box
                double angleDegrees = -30.0; // rotation angle

                addRotatedBoxToWorld(world, boxCenter, boxDimensions, angleDegrees, whiteWall);

                return world;
        }

        public static List<Triangle> makeQuad(Vector3 a, Vector3 b, Vector3 c, Vector3 d, Material material) {
                List<Triangle> triangles = new ArrayList<Triangle>();
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, a, b, c)); // Assume triangle positions are
                                                                                      // at the origin and they are only
                                                                                      // defined by the vertices
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, a, c, d));
                return triangles;
        }

        public static List<Triangle> makeBoxFromVertices(Vector3[] vertices, Material material) {
                if (vertices.length != 8) {
                        throw new IllegalArgumentException("There must be exactly 8 vertices.");
                }

                List<Triangle> triangles = new ArrayList<>();

                // Bottom (vertices[0], vertices[1], vertices[2], vertices[3])
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[0], vertices[1], vertices[2]));
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[0], vertices[2], vertices[3]));

                // Top (vertices[4], vertices[5], vertices[6], vertices[7])
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[4], vertices[5], vertices[6]));
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[4], vertices[6], vertices[7]));

                // Front (vertices[0], vertices[1], vertices[5], vertices[4])
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[0], vertices[1], vertices[5]));
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[0], vertices[5], vertices[4]));

                // Back (vertices[3], vertices[2], vertices[6], vertices[7])
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[3], vertices[2], vertices[6]));
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[3], vertices[6], vertices[7]));

                // Left (vertices[0], vertices[3], vertices[7], vertices[4])
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[0], vertices[3], vertices[7]));
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[0], vertices[7], vertices[4]));

                // Right (vertices[1], vertices[2], vertices[6], vertices[5])
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[1], vertices[2], vertices[6]));
                triangles.add(new Triangle(new Vector3(0, 0, 0), material, vertices[1], vertices[6], vertices[5]));

                return triangles;
        }

        // Method to rotate a point around the Y-axis by a given angle in degrees.
        public static Vector3 rotateAroundY(Vector3 point, double angleDegrees) {
                double angleRadians = Math.toRadians(angleDegrees);
                double cosAngle = Math.cos(angleRadians);
                double sinAngle = Math.sin(angleRadians);
                double x = point.getX() * cosAngle + point.getZ() * sinAngle;
                double y = point.getY(); // Y-coordinate remains unchanged
                double z = -point.getX() * sinAngle + point.getZ() * cosAngle;
                return new Vector3(x, y, z);
        }

        // Method to add the box to the world, including rotation, with separate
        // dimensions for width, height, and depth
        public static void addRotatedBoxToWorld(World world, Vector3 boxCenter, Vector3 dimensions, double angleDegrees,
                        Material material) {
                // Calculate half-dimensions for vertex positioning
                double halfWidth = dimensions.getX() / 2;
                double halfHeight = dimensions.getY() / 2;
                double halfDepth = dimensions.getZ() / 2;

                // Define the 8 vertices of the box based on the center and half-dimensions
                Vector3[] vertices = new Vector3[] {
                                // Bottom vertices
                                new Vector3(boxCenter.getX() - halfWidth, boxCenter.getY() - halfHeight,
                                                boxCenter.getZ() - halfDepth),
                                new Vector3(boxCenter.getX() + halfWidth, boxCenter.getY() - halfHeight,
                                                boxCenter.getZ() - halfDepth),
                                new Vector3(boxCenter.getX() + halfWidth, boxCenter.getY() - halfHeight,
                                                boxCenter.getZ() + halfDepth),
                                new Vector3(boxCenter.getX() - halfWidth, boxCenter.getY() - halfHeight,
                                                boxCenter.getZ() + halfDepth),
                                // Top vertices
                                new Vector3(boxCenter.getX() - halfWidth, boxCenter.getY() + halfHeight,
                                                boxCenter.getZ() - halfDepth),
                                new Vector3(boxCenter.getX() + halfWidth, boxCenter.getY() + halfHeight,
                                                boxCenter.getZ() - halfDepth),
                                new Vector3(boxCenter.getX() + halfWidth, boxCenter.getY() + halfHeight,
                                                boxCenter.getZ() + halfDepth),
                                new Vector3(boxCenter.getX() - halfWidth, boxCenter.getY() + halfHeight,
                                                boxCenter.getZ() + halfDepth),
                };

                // Apply the rotation transformation to each vertex
                for (int i = 0; i < vertices.length; i++) {
                        vertices[i] = rotateAroundY(vertices[i].subtractNew(boxCenter), angleDegrees).addNew(boxCenter);
                }

                // Create the triangles for the box and add them to the world
                List<Triangle> boxTriangles = makeBoxFromVertices(vertices, material);
                world.addRenderableObjects(boxTriangles);
        }

        public static World createHDRIWorld(IntersectionTester intersectionTester) {
                Camera camera = new Camera(
                                new Vector3(0, 0, 3.2),
                                new Vector3(0, 0, 0),
                                new Vector3(0, 1, 0),
                                90,
                                1,
                                1);

                World world = new World(camera, intersectionTester);

                Background background = new EquirectangularImageBackground(
                        new HDRImage("/home/anson/Documents/CS_455/Ray_Tracer_Java/RayTracerJava/textures/buikslotermeerplein_1k.exr"), 0
                );

                world.setBackground(background);

                AxisAlignedRectangularPrism floor = new AxisAlignedRectangularPrism(
                                new Vector3(0, -1, 0),
                                new Material(
                                                0.1,
                                                0.4,
                                                0.6,
                                                3,
                                                0.5,
                                                0.19,
                                                new TextureSurfaceColor(new Image(
                                                                "/home/anson/Documents/CS_455/Ray_Tracer_Java/RayTracerJava/wood_floor_1k/textures/wood_floor_diff_1k.jpg")),
                                                new SolidSurfaceColor(new Color(0.1, 0.1, 0.1)),
                                                0,
                                                0,
                                                0),
                                new Vector3(10, 0.01, 10));
                world.addRenderableObject(floor);

                // Add three spheres of red, green, and blue colors, and then a glass sphere in
                // the middle
                Material redMaterial = new Material(
                                0.2,
                                0.9,
                                0.0,
                                1,
                                0.3,
                                0.15,
                                new Color(0.9, 0.05, 0.05),
                                new Color(0.9, 0.1, 0.1),
                                0,
                                0,
                                0);
                Sphere redSphere = new Sphere(
                                new Vector3(1, 0, 0),
                                redMaterial,
                                0.25);
                world.addRenderableObject(redSphere);

                Material greenMaterial = new Material(
                                0.2,
                                .8,
                                0.0,
                                1,
                                0.3,
                                0.2,
                                new Color(0.05, 0.9, 0.05),
                                new Color(0.1, 0.9, 0.1),
                                0,
                                0,
                                0);
                Sphere greenSphere = new Sphere(
                                new Vector3(-1, 0, 0),
                                greenMaterial,
                                0.25);
                world.addRenderableObject(greenSphere);

                Material blueMaterial = new Material(
                                0.2,
                                1.0,
                                0.0,
                                1,
                                0.4,
                                0.3,
                                new Color(0.05, 0.05, 0.9),
                                new Color(0.1, 0.1, 0.9),
                                0,
                                0,
                                0);
                Sphere blueSphere = new Sphere(
                                new Vector3(0, 0, -1),
                                blueMaterial,
                                0.25);
                world.addRenderableObject(blueSphere);

                Material glassMaterial = new Material(
                                0.0,
                                0.0,
                                0.0,
                                1,
                                0.1,
                                0,
                                new Color(0.1, 0.05, 0.05),
                                new Color(1, 1, 1),
                                1,
                                0.09,
                                1.4);
                Sphere glassSphere = new Sphere(
                                new Vector3(0, 0, -.4),
                                glassMaterial,
                                1.1);
                world.addRenderableObject(glassSphere);

                return world;
        }

        public static World createHDRIWorldWithFStop(IntersectionTester intersectionTester, double fStop, double focusDistance) {
                Camera camera = new Camera(
                        new Vector3(0, 0, 3.2),
                        new Vector3(0, 0, 0),
                        new Vector3(0, 1, 0),
                        90,
                        1,
                        1,
                        fStop,
                        focusDistance
                );

                World world = new World(camera, intersectionTester);

                Background background = new EquirectangularImageBackground(
                        new HDRImage("/home/anson/Documents/CS_455/Ray_Tracer_Java/RayTracerJava/textures/buikslotermeerplein_1k.exr"), 0
                );

                world.setBackground(background);

                AxisAlignedRectangularPrism floor = new AxisAlignedRectangularPrism(
                        new Vector3(0, -1, 0),
                        new Material(
                                0.1,
                                0.4,
                                0.6,
                                3,
                                0.5,
                                0.19,
                                new TextureSurfaceColor(new Image(
                                        "/home/anson/Documents/CS_455/Ray_Tracer_Java/RayTracerJava/wood_floor_1k/textures/wood_floor_diff_1k.jpg")),
                                new SolidSurfaceColor(new Color(0.1, 0.1, 0.1)),
                                0,
                                0,
                                0),
                        new Vector3(10, 0.01, 10));
                world.addRenderableObject(floor);

                // Add three spheres of red, green, and blue colors, and then a glass sphere in
                // the middle
                Material redMaterial = new Material(
                        0.2,
                        0.9,
                        0.0,
                        1,
                        0.3,
                        0.15,
                        new Color(0.9, 0.05, 0.05),
                        new Color(0.9, 0.1, 0.1),
                        0,
                        0,
                        0);
                Sphere redSphere = new Sphere(
                        new Vector3(1, 0, 0),
                        redMaterial,
                        0.25);
                world.addRenderableObject(redSphere);

                Material greenMaterial = new Material(
                        0.2,
                        .8,
                        0.0,
                        1,
                        0.3,
                        0.2,
                        new Color(0.05, 0.9, 0.05),
                        new Color(0.1, 0.9, 0.1),
                        0,
                        0,
                        0);
                Sphere greenSphere = new Sphere(
                        new Vector3(-1, 0, 0),
                        greenMaterial,
                        0.25);
                world.addRenderableObject(greenSphere);

                Material blueMaterial = new Material(
                        0.2,
                        1.0,
                        0.0,
                        1,
                        0.4,
                        0.3,
                        new Color(0.05, 0.05, 0.9),
                        new Color(0.1, 0.1, 0.9),
                        0,
                        0,
                        0);
                Sphere blueSphere = new Sphere(
                        new Vector3(0, 0, -1),
                        blueMaterial,
                        0.25);
                world.addRenderableObject(blueSphere);

                Material glassMaterial = new Material(
                        0.0,
                        0.0,
                        0.0,
                        1,
                        0.1,
                        0,
                        new Color(0.1, 0.05, 0.05),
                        new Color(1, 1, 1),
                        1,
                        0.09,
                        1.4);
                Sphere glassSphere = new Sphere(
                        new Vector3(0, 0, -.4),
                        glassMaterial,
                        1.1);
                world.addRenderableObject(glassSphere);

                return world;
        }
}
