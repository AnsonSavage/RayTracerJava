package algorithm.intersection_optimizations;

import algorithm.intersection_optimizations.bvh.BoundingVolumeHierarchy;
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
}