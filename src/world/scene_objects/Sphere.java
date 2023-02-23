package world.scene_objects;

import utilities.Material;
import utilities.Ray;
import utilities.Vector3;

public class Sphere extends RenderableObject {
    private double radius;

    public Sphere(Vector3 position, Material material, double radius) {
        super(position, material);
        this.radius = radius;
    }

    @Override
    public Vector3 getNormal(Vector3 positionOnSurface) {
        Vector3 normal = positionOnSurface.subtractNew(position);
        normal.normalize();
        return normal;
    }

    @Override
    public double getRayIntersectionParameter(Ray ray) {
        assert ray.isNormalized();
        /**
         * So, what we're going to do here is basically a quadratic equation.
         * We begin by solving (x - x_0)^2 + (y - y_0)^2 + (z - z_0)^2 = r^2
         * We have that (x, y, z) = (r_0x, r_0y, r_0z) + t * (r_dx, r_dy, r_dz)
         * So, we solve for t:
         * (r_0x + t * r_dx - x_0)^2 + (r_0y + t * r_dy - y_0)^2 + (r_0z + t * r_dz - z_0)^2 = r^2
         * (r_0x - x_0)^2 + 2 * t * (r_0x - x_0) * r_dx + t^2 * r_dx^2 + (r_0y - y_0)^2 + 2 * t * (r_0y - y_0) * r_dy + t^2 * r_dy^2 + (r_0z - z_0)^2 + 2 * t * (r_0z - z_0) * r_dz + t^2 * r_dz^2 = r^2
         * (r_dx^2 + r_dy^2 + r_dz^2) * t^2 + 2 * (r_0x * r_dx - r_0x * x_0 + r_0y * r_dy - r_0y * y_0 + r_0z * r_dz - r_0z * z_0) * t + (r_0x^2 - 2 * r_0x * x_0 + x_0^2 + r_0y^2 - 2 * r_0y * y_0 + y_0^2 + r_0z^2 - 2 * r_0z * z_0 + z_0^2) = r^2
         * So the quadratic form of this is
         * A = r_dx^2 + r_dy^2 + r_dz^2 (which is 1 because we've asserted the ray is normalize)
         * B = 2 * (r_0x * r_dx - r_0x * x_0 + r_0y * r_dy - r_0y * y_0 + r_0z * r_dz - r_0z * z_0)
         * C = r_0x^2 - 2 * r_0x * x_0 + x_0^2 + r_0y^2 - 2 * r_0y * y_0 + y_0^2 + r_0z^2 - 2 * r_0z * z_0 + z_0^2 - r^2 because we're solving for 0
         * Then t = (-B +- sqrt(B^2 - 4 * A * C)) / (2 * A)
         * or, because A = 1
         * t = (-B +- sqrt(B^2 - 4 * C)) / 2
         */
        Vector3 rayOrigin = ray.getOrigin();
        Vector3 rayDirection = ray.getDirection();
        Vector3 sphereOrigin = position;
        double B = computeB(rayOrigin, rayDirection, sphereOrigin);
        double C = computeC(rayOrigin, sphereOrigin, radius);
        double discriminant = computeDiscriminant(B, C);

        if (discriminant < 0) { // This means that there are no real solutions
            return Double.POSITIVE_INFINITY;
        }
    }

    private double computeB(Vector3 rayOrigin, Vector3 rayDirection, Vector3 sphereOrigin) {
//        return 2 * (rayOrigin.getX() * rayDirection.getX() - rayOrigin.getX() * sphereOrigin.getX() + rayOrigin.getY() * rayDirection.getY() - rayOrigin.getY() * sphereOrigin.getY() + rayOrigin.getZ() * rayDirection.getZ() - rayOrigin.getZ() * sphereOrigin.getZ());
        double rayOriginDotDirection = rayOrigin.dot(rayDirection);
        double rayDirectionDotSphereOrigin = rayDirection.dot(sphereOrigin);
        double B = 2 * (rayOriginDotDirection - rayDirectionDotSphereOrigin);
        return B;
    }

    private double computeC(Vector3 rayOrigin, Vector3 sphereOrigin, double radius) {
        double rayOriginDotRayOrigin = rayOrigin.dot(rayOrigin);
        double rayOriginDotSphereOrigin = rayOrigin.dot(sphereOrigin);
        double sphereOriginDotSphereOrigin = sphereOrigin.dot(sphereOrigin);
        double C = rayOriginDotRayOrigin - 2 * rayOriginDotSphereOrigin + sphereOriginDotSphereOrigin - radius * radius;
        return C;
    }

    private double computeDiscriminant(double B, double C) {
        return B * B - 4 * C;
    }
}
