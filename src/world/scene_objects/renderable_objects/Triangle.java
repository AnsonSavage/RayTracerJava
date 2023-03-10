package world.scene_objects.renderable_objects;

import utilities.Material;
import utilities.Ray;
import utilities.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Triangle extends RenderableObject {
    private List<Vector3> vertices;
    private final Vector3 normal;
    private Vector3 normalForIntersectionTests;
    private double d;
    private boolean vertexOrderReversed = false;
    public Triangle(Vector3 position, Material material, Vector3 v1, Vector3 v2, Vector3 v3) {
        super(position, material);
        // Offset each of v1, v2, and v3 by position... If an orientation vector were implemented, we'd do the same thing here:)
        v1.add(position);
        v2.add(position);
        v3.add(position);

        vertices = new ArrayList<>();
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);

        assert vertices.size() == 3; // This is a triangle, so it should have 3 vertices
        normal = computeNormal();
        normalForIntersectionTests = normal.copy();
        d = -normalForIntersectionTests.dot(vertices.get(0)); // We can take any of the vertices, since they're all on the plane
    }

    @Override
    public Vector3 getNormal(Vector3 positionOnSurface) {
        return normal; // Triangles are flat, so the normal is constant
    }

    @Override
    public double getRayIntersectionParameter(Ray ray) {
        double t = getPlaneIntersectionParameter(ray); // Now we need to test if this point lies within the triangle
        if (t == -1) {
            return -1;
        }

        Vector3 pointOfIntersection = ray.getRayEnd(t);

        for (int i = 0; i < vertices.size(); i++) {
            Vector3 currentVertex = vertices.get(i);
            Vector3 nextVertex = vertices.get((i + 1) % vertices.size());

            Vector3 edge = nextVertex.subtractNew(currentVertex);
            Vector3 pointToVertex = pointOfIntersection.subtractNew(currentVertex);

            if (edge.cross(pointToVertex).dot(normalForIntersectionTests) < 0) {
                return -1; // The point of intersection is outside the triangle
            }
        }

        if (vertexOrderReversed) {
            flipNormal();
        }

        assert !vertexOrderReversed;

        return t;
    }

    @Override
    public void scale(double scaleFactor) {
        for (Vector3 vertex : vertices) {
            vertex.multiply(scaleFactor);
        }
    }

    private double getPlaneIntersectionParameter(Ray ray) {
        Vector3 rayOrigin = ray.getOrigin();
        Vector3 rayDirection = ray.getDirection();

        // Imagine the plane equation is ax + by + cz + d = 0. Then
        double normalDotRayDirection = normalForIntersectionTests.dot(rayDirection);

        if (normalDotRayDirection == 0) {
            return -1; // The ray is parallel to the plane, and does not intersect
        }

        if (normalDotRayDirection > 0) {
            flipNormal();
//            normalDotRayDirection = normalForIntersectionTests.dot(rayDirection);
//            return -1;
        }

        double t = -(normalForIntersectionTests.dot(rayOrigin) + d) / normalDotRayDirection;

        if (t < 0) {
            return -1; // The ray is pointing away from the triangle
        }

        return t;
    }

    private Vector3 computeNormal() {
        Vector3 v1 = vertices.get(0);
        Vector3 v2 = vertices.get(1);
        Vector3 v3 = vertices.get(2);

        Vector3 edge0 = v2.subtractNew(v1);
        Vector3 edge1 = v3.subtractNew(v1);
        Vector3 normal = edge0.cross(edge1);
        normal.normalize();
        return normal;
    }

    private void flipNormal() {
//        normalForIntersectionTests.multiply(-1);
        // Reverse the vertices
        Collections.reverse(vertices);
        vertexOrderReversed = !vertexOrderReversed;
    }
}
