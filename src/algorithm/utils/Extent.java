package algorithm.utils;

import algorithm.Hittable;
import utilities.Ray;
import utilities.Vector3;

public class Extent implements Hittable {
    Vector3 min;
    Vector3 max;

    /**
     * Constructor for Extent. This essentially is an axis aligned bounding box.
     * @param minX
     * @param minY
     * @param minZ
     * @param maxX
     * @param maxY
     * @param maxZ
     */
    public Extent(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.min = new Vector3(minX, minY, minZ);
        this.max = new Vector3(maxX, maxY, maxZ);
    }

    public Extent(Vector3 min, Vector3 max) {
        this.min = min;
        this.max = max;
    }

    public Vector3 getMin() {
        return min;
    }

    public Vector3 getMax() {
        return max;
    }

    protected double getRayIntersectionParameter(Ray ray) {
        double tMin = Double.NEGATIVE_INFINITY;
        double tMax = Double.POSITIVE_INFINITY;

        for (int dim = 0; dim < 3; dim++) {
            if (ray.getDirection().getValueByIndex(dim) == 0) { // In this case, the ray is parallel to the current plane
                if (ray.getOrigin().getValueByIndex(dim) < min.getValueByIndex(dim) || ray.getOrigin().getValueByIndex(dim) > max.getValueByIndex(dim)) {
                    return -1;
                }
            }
            else {
                double t1 = (min.getValueByIndex(dim) - ray.getOrigin().getValueByIndex(dim)) / ray.getDirection().getValueByIndex(dim);
                double t2 = (max.getValueByIndex(dim) - ray.getOrigin().getValueByIndex(dim)) / ray.getDirection().getValueByIndex(dim);

                double tNear = Math.min(t1, t2);
                double tFar = Math.max(t1, t2);

                if (tNear > tMin) {
                    tMin = tNear;
                }
                if (tFar < tMax) {
                    tMax = tFar;
                }

                if (tMin > tMax) {
                    return -1;
                }
            }
        }
        return tMin;
    }

    private boolean isPointInExtent(Vector3 point) {
        return point.getX() >= min.getX() && point.getX() <= max.getX() &&
                point.getY() >= min.getY() && point.getY() <= max.getY() &&
                point.getZ() >= min.getZ() && point.getZ() <= max.getZ();
    }

    public boolean isHit(Ray ray) { // Also returns true if the ray starts inside the extent
        return isPointInExtent(ray.getOrigin()) || getRayIntersectionParameter(ray) >= 0;
    }
}
