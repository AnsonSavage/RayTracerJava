package world.scene_objects.renderable_objects;

import algorithm.utils.Extent;
import utilities.Material;
import utilities.Ray;
import utilities.UVCoordinates;
import utilities.Vector3;

public class Sphere extends RenderableObject implements Surface {
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
        double t;

        if (discriminant < 0) { // This means that there are no real solutions
            return -1;
        }
        else if (discriminant == 0) {
            return -B / 2; // This is the solution to the simplified quadratic when the discriminant is 0
        }
        else {
            double t1 = (-B - Math.sqrt(discriminant)) / 2;
            if (t1 <= 0) {
                double t2 = (-B + Math.sqrt(discriminant)) / 2;
                if (t2 <= 0) {
                    return -1; // Both solutions are negative, so there is no intersection
                }
                else { // In this case, t2 is the only positive solution
                    t = t2;
                }
            }
            else { // In this case, t1 is the only positive solution and t1 is the smaller of the two solutions
                t = t1;
            }
        }

        return t;
    }

    @Override
    public void scale(double scaleFactor) {
        assert scaleFactor > 0;
        radius *= scaleFactor;
    }

    @Override
    public Extent getExtent() {
        return new Extent(
                position.getX() - radius,
                position.getY() - radius,
                position.getZ() - radius,
                position.getX() + radius,
                position.getY() + radius,
                position.getZ() + radius
        );
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

    @Override
    public Vector3 sampleSurface() {
        double theta = Math.random() * 2 * Math.PI;
        double u = Math.random();
        double phi = Math.acos(1 - 2 * u); // According to ChatGPT, this is needed to unbias the phi term
        double x = position.getX() + radius * Math.sin(phi) * Math.cos(theta);
        double y = position.getY() + radius * Math.sin(phi) * Math.sin(theta);
        double z = position.getZ() + radius * Math.cos(phi);
        return new Vector3(x, y, z);
    }

    @Override
    public UVCoordinates getTextureCoordinates(Vector3 positionOnSurface) {
        if (!this.getMaterial().isTextured()) { // If the material is not textured, then there are no texture coordinates
            return null;
        }

        // Compute vector from sphere's center to the surface position
        Vector3 normal = this.getNormal(positionOnSurface);

        // Calculate azimuthal angle (theta) and elevation angle (phi)
        double theta = Math.atan2(normal.getY(), normal.getX());
        double phi = Math.acos(normal.getZ());

        // Normalize theta and phi to the [0, 1] range
        double u = phi / Math.PI;
        double v = (theta + Math.PI) / (2 * Math.PI);

        return new UVCoordinates(u, v);
    }
}
