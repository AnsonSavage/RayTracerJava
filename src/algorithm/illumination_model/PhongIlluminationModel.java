package algorithm.illumination_model;

import utilities.Color;
import utilities.Material;
import utilities.Ray;
import utilities.Vector3;
import world.background.Background;
import world.scene_objects.light.Light;

import java.util.List;

public class PhongIlluminationModel {
    private Material material;
    private Ray ray;
    private Vector3 normal;
    private Vector3 positionOnSurface;
    private List<Light> lights;
    private Background background;

    private Color computeAmbientComponent() {

    }

    private Color computeSpecularComponent() {

    }

    private Color computeDiffuseComponent() {

    }

    public Color computeColor() {

    }
}
