package algorithm.utils;

import utilities.Ray;
import utilities.Vector3;

import static org.junit.jupiter.api.Assertions.*;

class ExtentTest {

    @org.junit.jupiter.api.Test
    void testRayIntersectionWithInfinitelyThinExtent() {
        Extent infinitelyThinExtent = new Extent(
                new Vector3(0, 0, 0),
                new Vector3(2, 2, 0)
        );

        Ray intersectionRay = new Ray(
                new Vector3(1, 1, 2),
                new Vector3(0, 0, -1)
        );

        assertTrue(infinitelyThinExtent.isHit(intersectionRay));
        assertTrue(MathUtils.isClose(2, infinitelyThinExtent.getRayIntersectionParameter(intersectionRay)));
    }

    @org.junit.jupiter.api.Test
    void testMissInfinitelyThinExtent() {
        Extent infinitelyThinExtent = new Extent(
                new Vector3(0, 0, 0),
                new Vector3(2, 2, 0)
        );

        Ray intersectionRay = new Ray(
                new Vector3(3, 3, 2),
                new Vector3(0, 0, -1)
        );

        assertFalse(infinitelyThinExtent.isHit(intersectionRay));
        assertEquals(-1, infinitelyThinExtent.getRayIntersectionParameter(intersectionRay));
    }

    @org.junit.jupiter.api.Test
    void testHitBox() {
        Extent boxExtent = new Extent(
                new Vector3(-1, -1, -1),
                new Vector3(1, 1, 1)
        );

        Ray intersectionRay = new Ray(
                new Vector3(0, -2, 0),
                new Vector3(0, 1, 0)
        );

        assertTrue(boxExtent.isHit(intersectionRay));
    }

    @org.junit.jupiter.api.Test
    void testMissBox() {
        Extent boxExtent = new Extent(
                new Vector3(-1, -1, -1),
                new Vector3(1, 1, 1)
        );

        Ray intersectionRay = new Ray(
                new Vector3(0, -2, 0),
                new Vector3(1.5, 1, 0)
        );

        assertFalse(boxExtent.isHit(intersectionRay));
    }

    @org.junit.jupiter.api.Test
    void testExtentIntersections() {
        Extent extent1 = new Extent(
                new Vector3(-6, -1, -1),
                new Vector3(-4, 1, 1)
        );

        Extent extent2 = new Extent(
                new Vector3(-11, -1, -1),
                new Vector3(0, 1, 1)
        );

        assertTrue(extent1.isIntersectingOtherExtent(extent2));
        assertTrue(extent2.isIntersectingOtherExtent(extent1));
    }

    @org.junit.jupiter.api.Test
    void testScaleFromCenter() {
        Extent extent = new Extent(
                new Vector3(-1, -1, -1),
                new Vector3(1, 1, 1)
        );

        extent.scaleFromCenter(2);

        assertEquals(new Vector3(-2, -2, -2), extent.getMin());
        assertEquals(new Vector3(2, 2, 2), extent.getMax());
    }

    @org.junit.jupiter.api.Test
    void testScaleFromCenterNotAtOrigin() {
        Extent extent = new Extent(
                new Vector3(1, 1, 1),
                new Vector3(3, 3, 3)
        );

        extent.scaleFromCenter(2);

        assertEquals(new Vector3(0, 0, 0), extent.getMin());
        assertEquals(new Vector3(4, 4, 4), extent.getMax());
    }
}