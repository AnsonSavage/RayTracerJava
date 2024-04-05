package algorithm;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.World;
import world.scene_objects.Camera;

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
        if (pixelX == 157 && pixelY == 249) {
            System.out.println("Hit our favorite pixel!");
        }
        Color finalColor = new Color(0, 0, 0); // Initialize the final color of the pixel to black
        double subPixelOffset = 1.0 / settings.getSquareSamplesPerPixel();
        for (int i = 0; i < settings.getSquareSamplesPerPixel(); i++) {
            double subPixelXOffset = subPixelOffset * i;
            for (int j = 0; j < settings.getSquareSamplesPerPixel(); j++) {
                double subPixelYOffset = subPixelOffset * j;
                Ray ray = getRayDirection(pixelX + subPixelXOffset, pixelY + subPixelYOffset);

                // Adjust ray for depth of field, if applicable
                Camera camera = world.getCamera();
                if (camera.getfStop() != Double.MAX_VALUE) {
                    double maxJitterAmount = camera.getAperatureSize();
                    double rayFocusPointT = camera.getFocusDistance() / (camera.getLookAtVector().dot(ray.getDirection()));
                    ray.jitterRayOriginAndDirection(maxJitterAmount, rayFocusPointT);
                }

                Color rayContribution = traceRay(ray);
                finalColor.add(rayContribution);
            }
        }
        return finalColor.multiplyNew(1.0 / (settings.getSquareSamplesPerPixel() * settings.getSquareSamplesPerPixel()));
    }

    @Override
    protected Vector3 getPixelWorldSpaceLocation(double pixelX, double pixelY) {
        if (!isInitialized) {
            initialize();
        }
        double xOffset = pixelX * xIncrement + xIncrement * random.nextDouble() / settings.getSquareSamplesPerPixel();
        double yOffset = -1 * (pixelY * yIncrement + yIncrement * random.nextDouble() / settings.getSquareSamplesPerPixel());

        Vector3 worldSpaceLocation = topLeftImagePlanePosition.addNew(new Vector3(xOffset, yOffset, 0)); // TODO: This assumes the viewing plane is always in the XY plane

        return worldSpaceLocation;
    }
}
