package algorithm;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.World;

import java.util.Random;

public class MultiSampleRayTracer extends SimpleRecursiveRayTracer {
    private Random random = new Random();

    public MultiSampleRayTracer(RenderSettings settings, World world) {
        super(settings, world);
    }

    public MultiSampleRayTracer(RenderSettings settings, World world, boolean isMultithreaded) {
        super(settings, world, isMultithreaded);
    }

    @Override
    protected Color computePixelValue(int pixelX, int pixelY) {
        Color finalColor = new Color(0, 0, 0);
        for (int i = 0; i < settings.getSamplesPerPixel(); i++) {
            Ray ray = getRayDirection(pixelX, pixelY);
            Color rayContribution = traceRay(ray).multiplyNew(1 / (double) settings.getSamplesPerPixel());
            finalColor.add(rayContribution);
        }
        return finalColor;
    }

    @Override
    protected Vector3 getPixelWorldSpaceLocation(int pixelX, int pixelY) {
        if (!isInitialized) {
            initialize();
        }
        double xOffset = pixelX * xIncrement + xIncrement * random.nextDouble();
        double yOffset = -1 * (pixelY * yIncrement + yIncrement * random.nextDouble());

        Vector3 worldSpaceLocation = topLeftImagePlanePosition.addNew(new Vector3(xOffset, yOffset, 0)); // TODO: This assumes the viewing plane is always in the XY plane

        return worldSpaceLocation;
    }
}
