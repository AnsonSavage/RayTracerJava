package algorithm;

import utilities.Ray;

public interface Hittable {
    public boolean isHit(Ray ray);
}
