package utilities;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
    @org.junit.jupiter.api.Test
    void testMaxJitteredAngle() {
        Vector3 rayOrigin = new Vector3(0, 0, 0);
        Ray ray = new Ray(rayOrigin, new Vector3(0, 1, 0));

        List<Ray> jitteredRays = ray.getNJitteredRays(90, 10000);

        for (Ray jitteredRay : jitteredRays) {
            if (jitteredRay.getDirection().dot(ray.getDirection()) < 0) {
                System.out.println("Jittered Ray Direction " + jitteredRay.getDirection());
            }
            assertTrue(jitteredRay.getDirection().dot(ray.getDirection()) >= 0);
        }
    }
}