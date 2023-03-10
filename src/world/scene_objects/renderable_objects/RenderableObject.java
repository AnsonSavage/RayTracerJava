package world.scene_objects.renderable_objects;

import utilities.Material;
import utilities.Ray;
import utilities.Vector3;
import world.scene_objects.WorldObject;

public abstract class RenderableObject extends WorldObject {
    private Material material;

    public RenderableObject(Vector3 position, Material material) {
        super(position);
        this.material = material;
    }

    public abstract Vector3 getNormal(Vector3 positionOnSurface);

    /**
     * Returns the parameter t such that the point of intersection is given by ray.getPoint(t)
     * @param ray
     * @return the parameter t such that the point of intersection is given by ray.getPoint(t), or -1 if there is no intersection
     */
    public abstract double getRayIntersectionParameter(Ray ray);

    public Material getMaterial() {
        return material;
    }

    public abstract void scale(double scaleFactor);
}
