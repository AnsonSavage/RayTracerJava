package algorithm.intersection_optimizations;

import algorithm.intersection_optimizations.bvh.BoundingVolumeHierarchy;
import algorithm.utils.Extent;
import algorithm.utils.MathUtils;
import algorithm.utils.ObjectDistancePair;
import utilities.Ray;
import utilities.Vector3;
import world.scene_objects.renderable_objects.Sphere;
import world.scene_objects.renderable_objects.Triangle;

import static org.junit.jupiter.api.Assertions.*;

class MedianSplitIntersectionTesterTest {
    @org.junit.jupiter.api.Test
    void testGetClosestObjectSingleSphere() {
        Sphere sphere = new Sphere(
                new Vector3(0, 0, 0),
                null,
                1
        );

        MedianSplitIntersectionTester medianSplitIntersectionTester = new MedianSplitIntersectionTester();

        medianSplitIntersectionTester.addRenderableObject(sphere);

        Ray intersectionRay = new Ray(
                new Vector3(0, 0, 2),
                new Vector3(0, 0, -1)
        );

        ObjectDistancePair closestObject = medianSplitIntersectionTester.getClosestObject(intersectionRay);

        assertEquals(sphere, closestObject.getObject());
        assertTrue(MathUtils.isClose(1, closestObject.getDistance()));
    }

    @org.junit.jupiter.api.Test
    void testGetClosestObjectSingleTriangle() {
        MedianSplitIntersectionTester medianSplitIntersectionTester = new MedianSplitIntersectionTester();

        Triangle triangle = new Triangle(
                new Vector3(0, 0, 0),
                null,
                new Vector3(1, 0, -1),
                new Vector3(0, 0, 1),
                new Vector3(-1, 0, -1)
        );

        medianSplitIntersectionTester.addRenderableObject(triangle);

        Ray intersectionRay = new Ray(
                new Vector3(0, -2, 0),
                new Vector3(0, 1, 0)
        );

        ObjectDistancePair closestObject = medianSplitIntersectionTester.getClosestObject(intersectionRay);

        assertEquals(closestObject.getObject(), triangle);
        assertTrue(MathUtils.isClose(2, closestObject.getDistance()));
    }

    @org.junit.jupiter.api.Test
    void testHitExtentButMissObject() {
        MedianSplitIntersectionTester medianSplitIntersectionTester = new MedianSplitIntersectionTester();

        Triangle triangle = new Triangle(
                new Vector3(0, 0, 0),
                null,
                new Vector3(1, 0, -1),
                new Vector3(0, 0, 1),
                new Vector3(-1, 0, -1)
        );

        medianSplitIntersectionTester.addRenderableObject(triangle);

        Ray intersectionRay = new Ray( // This ray is intended to hit the bounding box of the object but miss the object
                new Vector3(0.5, -2, 0.5),
                new Vector3(0, 1, 0)
        );

        BoundingVolumeHierarchy bvh = medianSplitIntersectionTester.getBVH();

        assertTrue(bvh.getRoot().getExtent().isHit(intersectionRay)); // Ensure it hits the root's bounding box
        assertTrue(triangle.getExtent().isHit(intersectionRay)); // Ensure it hits the triangle's bounding box

        ObjectDistancePair closestObject = medianSplitIntersectionTester.getClosestObject(intersectionRay);

        assertNull(closestObject.getObject()); // Ensure it misses the triangle itself
    }

    @org.junit.jupiter.api.Test
    void testHitWithMultipleObjects() {
        MedianSplitIntersectionTester medianSplitIntersectionTester = new MedianSplitIntersectionTester();

        // Create a sphere at (-4, 0, 0) and a triangle at the same place as last time
        Sphere sphere = new Sphere(
                new Vector3(-4, 0, 0),
                null,
                1
        );
        medianSplitIntersectionTester.addRenderableObject(sphere);

        Triangle triangle = new Triangle(
                new Vector3(0, 0, 0),
                null,
                new Vector3(1, 0, -1),
                new Vector3(0, 0, 1),
                new Vector3(-1, 0, -1)
        );


        medianSplitIntersectionTester.addRenderableObject(triangle);

        // So, if we shoot a ray starting at -6, 0, 0 and going to 0, 1, 0, it should miss everything. Let's make sure that's true
        assertFalse(
                medianSplitIntersectionTester.getBVH().getRoot().getExtent().isHit(
                        new Ray(
                                new Vector3(-6, 0, 0),
                                new Vector3(0, 1, 0)
                        )
                )
        );

        // But sending a ray down the middle should hit the world bounding box
        assertTrue(
                medianSplitIntersectionTester.getBVH().getRoot().getExtent().isHit(
                        new Ray(
                                new Vector3(0, -2, 0),
                                new Vector3(0, 1, 0)
                        )
                )
        );

        // Now we should make sure that if we send a ray between the sphere and the triangle that we don't hit anything
        assertNull(
                medianSplitIntersectionTester.getClosestObject(
                        new Ray(
                                new Vector3(-2, -2, 0),
                                new Vector3(0, 1, 0)
                        )
                ).getObject()
        );


        // Okay, cool. Now we should make sure that we can independently hit the sphere and the triangle from our medianSplitIntersectionTester
        assertEquals(
                sphere,
                medianSplitIntersectionTester.getClosestObject(
                        new Ray(
                                new Vector3(-4, -2, 0),
                                new Vector3(0, 1, 0)
                        )
                ).getObject()
        );

        // Now for the triangle:
        assertEquals(
                triangle,
                medianSplitIntersectionTester.getClosestObject(
                        new Ray(
                                new Vector3(0, -2, 0),
                                new Vector3(0, 1, 0)
                        )
                ).getObject()
        );
    }

    @org.junit.jupiter.api.Test
    void testBuildBVHTwoLevels() {
        MedianSplitIntersectionTester medianSplitIntersectionTester = new MedianSplitIntersectionTester();
        // Let's add four spheres to the medianSplitIntersectionTester at x = -10, -5, 5, and 10

        Sphere sphere1 = new Sphere(
                new Vector3(-10, 0, 0),
                null,
                1
        );
        Sphere sphere2 = new Sphere(
                new Vector3(-5, 0, 0),
                null,
                1
        );
        Sphere sphere3 = new Sphere(
                new Vector3(5, 0, 0),
                null,
                1
        );
        Sphere sphere4 = new Sphere(
                new Vector3(10, 0, 0),
                null,
                1
        );

        medianSplitIntersectionTester.addRenderableObject(sphere1);
        medianSplitIntersectionTester.addRenderableObject(sphere2);
        medianSplitIntersectionTester.addRenderableObject(sphere3);
        medianSplitIntersectionTester.addRenderableObject(sphere4);

        medianSplitIntersectionTester.initialize(); // Initialize the medianSplitIntersectionTester

        // Ensure that the root's two children have the correct extents
        Extent extent1OfRoot = medianSplitIntersectionTester.getBVH().getRoot().getChildren().get(0).getExtent();
        Vector3 expectedMin1 = new Vector3(-11, -1, -1);
        Vector3 expectedMax1 = new Vector3(0, 1, 1);
        assertEquals(expectedMin1, extent1OfRoot.getMin());
        assertEquals(expectedMax1, extent1OfRoot.getMax());

        Extent extent2OfRoot = medianSplitIntersectionTester.getBVH().getRoot().getChildren().get(1).getExtent();
        Vector3 expectedMin2 = new Vector3(0, -1, -1);
        Vector3 expectedMax2 = new Vector3(11, 1, 1);
        assertEquals(expectedMin2, extent2OfRoot.getMin());
        assertEquals(expectedMax2, extent2OfRoot.getMax());

        // Make sure the root has two children
        assertEquals(2, medianSplitIntersectionTester.getBVH().getRoot().getChildren().size());

        // Make sure that the children both have two children
        assertEquals(2, medianSplitIntersectionTester.getBVH().getRoot().getChildren().get(0).getChildren().size());
        assertEquals(2, medianSplitIntersectionTester.getBVH().getRoot().getChildren().get(1).getChildren().size());
    }

    @org.junit.jupiter.api.Test
    void ensureRaysStillHitObjects() {
        // Setup
        MedianSplitIntersectionTester medianSplitIntersectionTester = new MedianSplitIntersectionTester();

        // Array to store sphere positions
        int[] positions = {-10, -5, 5, 10};
        Sphere[] spheres = new Sphere[positions.length];

        // Create and add spheres at specified positions
        for (int i = 0; i < positions.length; i++) {
            spheres[i] = new Sphere(
                    new Vector3(positions[i], 0, 0),
                    null,
                    1
            );
            medianSplitIntersectionTester.addRenderableObject(spheres[i]);
        }

        medianSplitIntersectionTester.initialize(); // Initialize the tester

        // Test rays hitting each sphere
        for (int i = 0; i < positions.length; i++) {
            ObjectDistancePair objectDistancePairIntersection = medianSplitIntersectionTester.getClosestObject(
                    new Ray(
                            new Vector3(positions[i], -2, 0),
                            new Vector3(0, 1, 0)
                    )
            );

            // Assert that the closest object is the expected sphere
            assertEquals(
                    spheres[i],
                    objectDistancePairIntersection.getObject()
            );

            // Assert that the distance is close to 1
            assertTrue(
                    MathUtils.isClose(1, objectDistancePairIntersection.getDistance())
            );
        }
    }

    @org.junit.jupiter.api.Test
    void ensureRaysStillHitObjectsFromMultipleDirections() {
        // Setup
        MedianSplitIntersectionTester medianSplitIntersectionTester = new MedianSplitIntersectionTester();

        // Sphere positions
        int[] positions = {-10, -5, 5, 10};
        Sphere[] spheres = new Sphere[positions.length];

        // Directions from which rays will be shot: positive and negative of x, y, and z axes
        Vector3[] directions = {
                new Vector3(1, 0, 0),  // Positive X
                new Vector3(-1, 0, 0), // Negative X
                new Vector3(0, 1, 0),  // Positive Y
                new Vector3(0, -1, 0), // Negative Y
                new Vector3(0, 0, 1),  // Positive Z
                new Vector3(0, 0, -1)  // Negative Z
        };

        // Create and add spheres
        for (int i = 0; i < positions.length; i++) {
            spheres[i] = new Sphere(
                    new Vector3(positions[i], 0, 0),
                    null,
                    1
            );
            medianSplitIntersectionTester.addRenderableObject(spheres[i]);
        }

        medianSplitIntersectionTester.initialize(); // Initialize the tester

        // Test rays hitting each sphere from multiple directions
        for (int i = 0; i < positions.length; i++) {
            for (Vector3 direction : directions) {
                // Adjust ray origin based on direction to ensure it starts off the object
                Vector3 origin = direction.multiplyNew(-3).addNew(new Vector3(positions[i], 0, 0));

                ObjectDistancePair objectDistancePairIntersection = medianSplitIntersectionTester.getClosestObject(
                        new Ray(origin, direction)
                );

                // Assert that the closest object is the expected sphere
                assertEquals(
                        spheres[i],
                        objectDistancePairIntersection.getObject(),
                        "Failed for sphere at " + positions[i] + " with direction " + direction
                );

                // No need to check distance here since it will vary with direction and starting point
            }
        }
    }
}