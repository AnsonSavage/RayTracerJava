package world.scene_objects;

import utilities.Material;

public class Sphere extends RenderableObject {
    private double radius;

    public Sphere(double radius, Material material) {
        super(material);
        this.radius = radius;
    }
}
