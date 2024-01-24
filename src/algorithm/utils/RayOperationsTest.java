package algorithm.utils;

import org.junit.jupiter.api.Assertions;
import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.light.SunLight;
import world.scene_objects.renderable_objects.Triangle;

class RayOperationsTest {

    @org.junit.jupiter.api.Test
    void getClosestObject() {
    }

    @org.junit.jupiter.api.Test
    void getShadowRays() {
    }

    @org.junit.jupiter.api.Test
    void getNonShadowCastingLights() {
    }

    @org.junit.jupiter.api.Test
    void isShadowRayInShadowForLight() {
        Ray shadowRay = new Ray(new Vector3(0, -1, 0), new Vector3(0, 1, 0));
        World world = new World();
        world.addRenderableObject(new Triangle(
                new Vector3(0, 0, 0),
                null,
                new Vector3(1, 0, -1),
                new Vector3(0, 0, 1),
                new Vector3(-1, 0, -1)
        ));
        for (int i = 0; i < 3000000; i++) {
            Assertions.assertTrue(RayOperations.canRayReachLight(shadowRay, world, new SunLight(
                    null,
                    null,
                    0,
                    null
            )));
        }
    }

    @org.junit.jupiter.api.Test
    void createReflectionRay() {
        Ray incomingRay = new Ray (new Vector3(0, 0, 1), new Vector3(0, -1, -1));
        Vector3 intersectionPoint = new Vector3(0, -1, 0);
        Vector3 normal = new Vector3(0, 1, 0);


        Ray outgoingRay = RayOperations.createReflectionRay(incomingRay, intersectionPoint, normal);

        Assertions.assertEquals(new Vector3(0, -1, 0), outgoingRay.getOrigin());

        Vector3 expectedDirection = new Vector3(0, 1, -1);
        expectedDirection.normalize();

        Assertions.assertEquals(expectedDirection, outgoingRay.getDirection());
    }

    @org.junit.jupiter.api.Test
    void createRefractionRay() {

    }
}