package algorithm;

import utilities.Ray;

public interface Hittable {
    public double getRayIntersectionParameter(Ray ray);
    public boolean isHit(Ray ray);
}
