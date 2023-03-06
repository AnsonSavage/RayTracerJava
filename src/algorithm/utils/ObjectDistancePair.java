package algorithm.utils;

import world.scene_objects.renderable_objects.RenderableObject;

public class ObjectDistancePair {
    public ObjectDistancePair(double distance, RenderableObject object) {
        this.distance = distance;
        this.object = object;
    }
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public RenderableObject getObject() {
        return object;
    }

    public void setObject(RenderableObject object) {
        this.object = object;
    }

    private double distance;


    private RenderableObject object;

}
