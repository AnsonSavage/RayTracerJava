package world.scene_objects.renderable_objects;

import utilities.UVCoordinates;
import utilities.Vector3;

public interface Surface {
    public Vector3 sampleSurface();

    public UVCoordinates getTextureCoordinates(Vector3 positionOnSurface);
}
