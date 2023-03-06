package world.scene_objects.renderable_objects;

import utilities.Material;
import utilities.Ray;
import utilities.Vector3;

public class Triangle extends RenderableObject {
    private Vector3[] vertices;
    private Vector3 normal;
    public Triangle(Vector3 position, Material material, Vector3 v1, Vector3 v2, Vector3 v3) {
        super(position, material);
        // Offset each of v1, v2, and v3 by position... If an orientation vector were implemented, we'd do the same thing here:)
        v1.add(position);
        v2.add(position);
        v3.add(position);

        vertices = new Vector3[]{v1, v2, v3};
        normal = computeNormal();
    }

    @Override
    public Vector3 getNormal(Vector3 positionOnSurface) {
        return normal;
    }

    @Override
    public double getRayIntersectionParameter(Ray ray) {
        // TODO: Implement this method
        return 0;
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
