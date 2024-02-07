package world.scene_objects.renderable_objects;


import algorithm.utils.Extent;
import utilities.Material;
import utilities.Ray;
import utilities.Vector3;

public class AxisAlignedRectangularPrism extends RenderableObject {
    private Extent extent; // Use Extent to represent the axis-aligned bounding box of the prism
    public AxisAlignedRectangularPrism(Vector3 position, Material material, Vector3 dimensions) {
        super(position, material);
        // Calculate the min and max points of the prism based on its dimensions and position
        Vector3 halfDimensions = dimensions.divideNew(2);
        Vector3 min = position.subtractNew(halfDimensions);
        Vector3 max = position.addNew(halfDimensions);
        this.extent = new Extent(min, max);
    }

    public AxisAlignedRectangularPrism(Extent extent, Material material) {
        super(extent.getCenter(), material);
        this.extent = extent;
    }

    @Override
    public Vector3 getNormal(Vector3 positionOnSurface) {
        Vector3 normal = new Vector3(0, 0, 0);
        Vector3 centerToSurface = positionOnSurface.subtractNew(this.position);
        double xDiff = Math.abs(centerToSurface.getX());
        double yDiff = Math.abs(centerToSurface.getY());
        double zDiff = Math.abs(centerToSurface.getZ());

        if (Math.abs(xDiff - this.extent.getMax().getX() + this.position.getX()) < Extent.EPSILON) {
            normal = new Vector3(1, 0, 0);
        } else if (Math.abs(-xDiff - this.extent.getMin().getX() + this.position.getX()) < Extent.EPSILON) {
            normal = new Vector3(-1, 0, 0);
        }

        if (Math.abs(yDiff - this.extent.getMax().getY() + this.position.getY()) < Extent.EPSILON) {
            normal = new Vector3(0, 1, 0);
        } else if (Math.abs(-yDiff - this.extent.getMin().getY() + this.position.getY()) < Extent.EPSILON) {
            normal = new Vector3(0, -1, 0);
        }

        if (Math.abs(zDiff - this.extent.getMax().getZ() + this.position.getZ()) < Extent.EPSILON) {
            normal = new Vector3(0, 0, 1);
        } else if (Math.abs(-zDiff - this.extent.getMin().getZ() + this.position.getZ()) < Extent.EPSILON) {
            normal = new Vector3(0, 0, -1);
        }

        return normal;
    }

    @Override
    public double getRayIntersectionParameter(Ray ray) {
        return extent.getRayIntersectionParameter(ray);
    }

    @Override
    public void scale(double scaleFactor) {
        extent.scaleFromCenter(scaleFactor);
    }

    @Override
    public Extent getExtent() {
        return extent;
    }
}
