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

    public boolean isPointInExtent(Vector3 point) {
        return point.getX() >= min.getX() && point.getX() <= max.getX() &&
                point.getY() >= min.getY() && point.getY() <= max.getY() &&
                point.getZ() >= min.getZ() && point.getZ() <= max.getZ();
    }

    public boolean isIntersectingOtherExtent(Extent otherExtent) {
        return otherExtent.isPointInExtent(min) || otherExtent.isPointInExtent(max) || isPointInExtent(otherExtent.min) || isPointInExtent(otherExtent.max);
    }

    public boolean isHit(Ray ray) { // Also returns true if the ray starts inside the extent
        return isPointInExtent(ray.getOrigin()) || getRayIntersectionParameter(ray) >= 0;
    }

    public int getLongestAxisIndex() {
        Vector3 extent = max.subtractNew(min);
        if (extent.getX() > extent.getY() && extent.getX() > extent.getZ()) {
            return 0;
        }
        else if (extent.getY() > extent.getZ()) {
            return 1;
        }
        else {
            return 2;
        }
    }

    public void scaleUpByEpsilon() {
        double epsilon = 0.001;
        this.scaleFromCenter(1 + epsilon);
    }
    public void scaleFromCenter(double scalar) {
        Vector3 center = min.addNew(max).multiplyNew(0.5); // Calculate center
        Vector3 minDiff = center.subtractNew(min).multiplyNew(scalar); // Calculate and scale difference for min
        Vector3 maxDiff = max.subtractNew(center).multiplyNew(scalar); // Calculate and scale difference for max

        this.min = center.subtractNew(minDiff); // Apply scaled difference to get new min
        this.max = center.addNew(maxDiff); // Apply scaled difference to get new max
    }

}
