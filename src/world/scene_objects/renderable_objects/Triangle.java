package world.scene_objects.renderable_objects;

import utilities.Material;
import utilities.Ray;
import utilities.Vector3;

public class Triangle extends RenderableObject {
    private Vector3[] vertices;
    private Vector3 normal;
    private boolean backfaceCulling;
    public Triangle(Vector3 position, Material material, Vector3 v1, Vector3 v2, Vector3 v3) {
        super(position, material);
        // Offset each of v1, v2, and v3 by position... If an orientation vector were implemented, we'd do the same thing here:)
        v1.add(position);
        v2.add(position);
        v3.add(position);

        vertices = new Vector3[]{v1, v2, v3};
        normal = computeNormal();
        backfaceCulling = true;
    }

    public Triangle(Vector3 position, Material material, Vector3 v1, Vector3 v2, Vector3 v3, boolean backfaceCulling) {
        this(position, material, v1, v2, v3);
        this.backfaceCulling = backfaceCulling;
    }

    @Override
    public Vector3 getNormal(Vector3 positionOnSurface) {
        return normal;
    }

    @Override
    public double getRayIntersectionParameter(Ray ray) { // ISSUE: This method isn't set up to work with our system because it doesn't take into account the position of the triangle
        Vector3 rayOrigin = ray.getOrigin();
        Vector3 rayDirection = ray.getDirection();

        // Imagine the plane equation is ax + by + cz + d = 0. Then
        double d = -normal.dot(vertices[0]); // We can take any of the vertices, since they're all on the plane
        double normalDotRayDirection = normal.dot(rayDirection);

        if (normalDotRayDirection == 0) {
            return -1; // The ray is parallel to the plane, and does not intersect
        }

        if (normalDotRayDirection > 0) {
            if (this.backfaceCulling) {
                return -1; // The ray is pointing away from the triangle, and we're backface culling
            } else {
                normal = normal.multiplyNew(-1); // Flip the normal
            }
        }

        double t = -(normal.dot(rayOrigin) + d) / normalDotRayDirection;

        if (t < 0) {
            return -1; // The ray is pointing away from the triangle
        }

        return t;
    }

    private Vector3 computeNormal() {
        Vector3 v1 = vertices[0];
        Vector3 v2 = vertices[1];
        Vector3 v3 = vertices[2];

        Vector3 edge0 = v2.subtractNew(v1);
        Vector3 edge1 = v3.subtractNew(v1);
        Vector3 normal = edge0.cross(edge1);
        normal.normalize();
        return normal;
    }
}
