package world.scene_objects.renderable_objects;

import algorithm.utils.Extent;
import utilities.Material;
import utilities.Ray;
import utilities.UVCoordinates;
import utilities.Vector3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Triangle extends RenderableObject implements Surface {
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

    private int getSign(double a) {
        if (a >= 0) {
            return 1;
        }
        return -1;
    }

    @Override
    public double getRayIntersectionParameter(Ray ray) {
        double t = getPlaneIntersectionParameter(ray); // Now we need to test if this point lies within the triangle
        if (t == -1) {
            return -1;
        }

        Vector3 pointOfIntersection = ray.getRayEnd(t);

        int sign = 0;

        for (int i = 0; i < vertices.size(); i++) {
            Vector3 currentVertex = vertices.get(i);
            Vector3 nextVertex = vertices.get((i + 1) % vertices.size());

            Vector3 edge = nextVertex.subtractNew(currentVertex);
            Vector3 pointToVertex = pointOfIntersection.subtractNew(currentVertex);

            if (i == 0) {
                sign = getSign(edge.cross(pointToVertex).dot(normalForIntersectionTests));
            } else {
                if (sign != getSign(edge.cross(pointToVertex).dot(normalForIntersectionTests))) {
                    return -1;
                }
            }
        }

        return t;
    }

    @Override
    public void scale(double scaleFactor) {
        for (Vector3 vertex : vertices) {
            vertex.multiply(scaleFactor);
        }
    }

    @Override
    public Extent getExtent() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double minZ = Double.POSITIVE_INFINITY;

        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        double maxZ = Double.NEGATIVE_INFINITY;

        for (Vector3 vertex : vertices) {
            if (vertex.getX() < minX) {
                minX = vertex.getX();
            }
            if (vertex.getY() < minY) {
                minY = vertex.getY();
            }
            if (vertex.getZ() < minZ) {
                minZ = vertex.getZ();
            }
            if (vertex.getX() > maxX) {
                maxX = vertex.getX();
            }
            if (vertex.getY() > maxY) {
                maxY = vertex.getY();
            }
            if (vertex.getZ() > maxZ) {
                maxZ = vertex.getZ();
            }
        }

        return new Extent(minX, minY, minZ, maxX, maxY, maxZ);
    }

    private double getPlaneIntersectionParameter(Ray ray) {
        Vector3 rayOrigin = ray.getOrigin();
        Vector3 rayDirection = ray.getDirection();

        // Imagine the plane equation is ax + by + cz + d = 0. Then
        double normalDotRayDirection = normalForIntersectionTests.dot(rayDirection);

        if (normalDotRayDirection == 0) {
            return -1; // The ray is parallel to the plane, and does not intersect
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

    @Override
    public Vector3 sampleSurface() {
        // We are going to compute this using barycentric coordinates, as per the suggestion of ChatGPT.
        double r1 = Math.random();
        double r2 = Math.random();

        if (r1+ r2 > 1) { // Ensure that they will produce valid barycentric coordinates
            r1 = 1 - r1;
            r2 = 1 - r2;
        }

        // Compute the weights
        double w1 = 1 - r1 - r2;
        double w2 = r1;
        double w3 = r2;

        double x = w1 * vertices.get(0).getX() + w2 * vertices.get(1).getX() + w3 * vertices.get(2).getX();
        double y = w1 * vertices.get(0).getY() + w2 * vertices.get(1).getY() + w3 * vertices.get(2).getY();
        double z = w1 * vertices.get(0).getZ() + w2 * vertices.get(1).getZ() + w3 * vertices.get(2).getZ();

        return new Vector3(x, y, z);
    }

    private Vector3 getBarycentricCoordinates(Vector3 positionOnSurface) {
        // v0 and v1 are vectors from the first vertex to the second and third vertices, respectively
        Vector3 v0 = vertices.get(1).subtractNew(vertices.get(0));
        Vector3 v1 = vertices.get(2).subtractNew(vertices.get(0));
        // v2 is the vector from the first vertex to the given surface position
        Vector3 v2 = positionOnSurface.subtractNew(vertices.get(0));

        // Compute dot products needed for the barycentric calculation
        double d00 = v0.dot(v0);
        double d01 = v0.dot(v1);
        double d11 = v1.dot(v1);
        double d20 = v2.dot(v0);
        double d21 = v2.dot(v1);

        // Compute the denominator of the barycentric coordinate formula
        // This value is equivalent to twice the area of the triangle
        double denominator = d00 * d11 - d01 * d01;

        // Calculate the barycentric coordinates for the given point on the surface
        // lambda2 corresponds to the weight of the second vertex
        double lambda2 = (d11 * d20 - d01 * d21) / denominator;
        // lambda3 corresponds to the weight of the third vertex
        double lambda3 = (d00 * d21 - d01 * d20) / denominator;
        // lambda1 is calculated so that the sum of all three weights is 1
        // It corresponds to the weight of the first vertex
        double lambda1 = 1.0 - lambda2 - lambda3;

        // Return the barycentric coordinates as a Vector3
        // Each component of the Vector3 represents one of the barycentric coordinates
        return new Vector3(lambda1, lambda2, lambda3);
    }

    @Override
    public UVCoordinates getTextureCoordinates(Vector3 positionOnSurface) {
        if (!this.getMaterial().isTextured()) { // If the material is not textured, then there are no texture coordinates
            return null;
        }
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
