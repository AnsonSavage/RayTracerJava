package world.scene_objects;

import utilities.Material;
import utilities.Vector3;

public class Sphere extends RenderableObject {
    private double radius;

    public Sphere(Vector3 position, Material material, double radius) {
        super(position, material);
        this.radius = radius;
    }
}
