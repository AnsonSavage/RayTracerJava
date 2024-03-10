package world.scene_objects.renderable_objects;


import algorithm.utils.Extent;
import utilities.Material;
import utilities.Ray;
import utilities.UVCoordinates;
import utilities.Vector3;

public class AxisAlignedRectangularPrism extends RenderableObject implements Surface {
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


    @Override
    public Vector3 sampleSurface() {
        // Calculate the area of each pair of faces (there are 3 pairs for an AABB)
        double areaXY = (extent.getMax().getX() - extent.getMin().getX()) * (extent.getMax().getY() - extent.getMin().getY());
        double areaXZ = (extent.getMax().getX() - extent.getMin().getX()) * (extent.getMax().getZ() - extent.getMin().getZ());
        double areaYZ = (extent.getMax().getY() - extent.getMin().getY()) * (extent.getMax().getZ() - extent.getMin().getZ());

        // Total surface area is the sum of all face areas (each area is counted twice since each face has a parallel counterpart)
        double totalSurfaceArea = 2 * (areaXY + areaXZ + areaYZ);

        // Generate a random number between 0 and the total surface area to decide which face to sample from
        double r = Math.random() * totalSurfaceArea;

        // Decide which face to sample from based on the random number
        Vector3 samplePoint = new Vector3();
        if (r < 2 * areaXY) {
            // Sample from one of the XY faces
            double x = Math.random() * (extent.getMax().getX() - extent.getMin().getX()) + extent.getMin().getX();
            double y = Math.random() * (extent.getMax().getY() - extent.getMin().getY()) + extent.getMin().getY();
            double z = (r < areaXY) ? extent.getMin().getZ() : extent.getMax().getZ(); // Choose which face based on r
            samplePoint = new Vector3(x, y, z);
        } else if (r < 2 * (areaXY + areaXZ)) {
            // Sample from one of the XZ faces
            double x = Math.random() * (extent.getMax().getX() - extent.getMin().getX()) + extent.getMin().getX();
            double z = Math.random() * (extent.getMax().getZ() - extent.getMin().getZ()) + extent.getMin().getZ();
            double y = (r < (2 * areaXY + areaXZ)) ? extent.getMin().getY() : extent.getMax().getY(); // Choose which face
            samplePoint = new Vector3(x, y, z);
        } else {
            // Sample from one of the YZ faces
            double y = Math.random() * (extent.getMax().getY() - extent.getMin().getY()) + extent.getMin().getY();
            double z = Math.random() * (extent.getMax().getZ() - extent.getMin().getZ()) + extent.getMin().getZ();
            double x = (r < (2 * (areaXY + areaXZ) + areaYZ)) ? extent.getMin().getX() : extent.getMax().getX(); // Choose which face
            samplePoint = new Vector3(x, y, z);
        }

        return samplePoint;
    }

    @Override
    public UVCoordinates getTextureCoordinates(Vector3 positionOnSurface) {

        if (!this.getMaterial().isTextured()) {
            return null;
        }
        // Let's imagine each face of the prism is a rectangle with u and v coordinates ranging from 0 to 1
        Vector3 normal = getNormal(positionOnSurface);

        if (normal.equals(new Vector3(1, 0, 0)) || normal.equals(new Vector3(-1, 0, 0))) {
            return new UVCoordinates((positionOnSurface.getY() - extent.getMin().getY()) / (extent.getMax().getY() - extent.getMin().getY()), (positionOnSurface.getZ() - extent.getMin().getZ()) / (extent.getMax().getZ() - extent.getMin().getZ()));
        } else if (normal.equals(new Vector3(0, 1, 0)) || normal.equals(new Vector3(0, -1, 0))) {
            return new UVCoordinates((positionOnSurface.getX() - extent.getMin().getX()) / (extent.getMax().getX() - extent.getMin().getX()), (positionOnSurface.getZ() - extent.getMin().getZ()) / (extent.getMax().getZ() - extent.getMin().getZ()));
        } else {
            return new UVCoordinates((positionOnSurface.getX() - extent.getMin().getX()) / (extent.getMax().getX() - extent.getMin().getX()), (positionOnSurface.getY() - extent.getMin().getY()) / (extent.getMax().getY() - extent.getMin().getY()));
        }
    }
}
