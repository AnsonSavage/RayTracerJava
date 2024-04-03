package algorithm;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.World;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public abstract class RayTracer extends RenderAlgorithm {
    protected boolean isInitialized = false;
    protected Vector3 topLeftImagePlanePosition;
    protected Vector3 cameraPosition;
    protected double xIncrement;
    protected double yIncrement;
    private boolean isMultiThreaded;


    public RayTracer(RenderSettings settings, World world, boolean isMultiThreaded) {
        super(settings, world);
        this.isMultiThreaded = isMultiThreaded;
        initialize();
    }
    public RayTracer(RenderSettings settings, World world) {
        this(settings, world, true);
    }

    @Override
    protected void renderImplementation() {
        if (isMultiThreaded) {
            multithreadedRenderImplementation();
        } else {
            singleThreadedRenderImplementation();
        }
    }

    private void singleThreadedRenderImplementation() {
        for (int i = 0; i < settings.getResolutionX(); i++) { // Each thread renders all x pixels for a given range of y pixels
            for (int j = 0; j < settings.getResolutionY(); j++) {
//                System.out.println("Computing pixel value for pixel" + i + ", " + j);
//                if (i == 250 && j == 250) {
//                    System.out.println("Color at pixel 250, 250: ");
//                }
                Color color = computePixelValue(i, j);
                image.setPixel(i, j, color);
            }
        }
    }

    private void multithreadedRenderImplementation() {
        // I asked ChatGPT to help me multithread the code from above, and this is what it came up with:
        double startTime = System.currentTimeMillis();
        int numThreads = Runtime.getRuntime().availableProcessors(); // get number of available processors
        int chunkSize = settings.getResolutionY() / numThreads; // divide the image into chunks
        int totalPixels = settings.getResolutionX() * settings.getResolutionY();

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int t = 0; t < numThreads; t++) {
            final int start = t * chunkSize;
            final int end = (t == numThreads - 1) ? settings.getResolutionY() : (t + 1) * chunkSize;

            executor.execute(() -> {
                for (int pixelX = 0; pixelX < settings.getResolutionX(); pixelX++) { // Each thread renders all x pixels for a given range of y pixels
                    for (int pixelY = start; pixelY < end; pixelY++) {
                        Color color = computePixelValue(pixelX, pixelY);
                        image.setPixel(pixelX, pixelY, color);
//                        System.out.println("Rendered pixel: " + (i * settings.getResolutionY() + j) + "/" + totalPixels + "");
                    }
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Rendering completed in " + ((System.currentTimeMillis() - startTime)/1000) + " seconds, using " + numThreads + " threads");

    }

    protected Color computePixelValue(int pixelX, int pixelY) {
        Ray ray = getRayDirection(pixelX, pixelY);
        return traceRay(ray);
    }

    protected Ray getRayDirection(double pixelX, double pixelY) {
        Vector3 pixelRayEnd = getPixelWorldSpaceLocation(pixelX, pixelY);

        Vector3 rayDirection = pixelRayEnd.subtractNew(cameraPosition);
        rayDirection.normalize(); // Normalize ray direction for simplicity of code when testing sphere intersection

        return new Ray(cameraPosition, rayDirection);
    }

    protected Vector3 getPixelWorldSpaceLocation(double pixelX, double pixelY) {
        if (!isInitialized) {
            initialize();
        }
        double xOffset = pixelX * xIncrement + xIncrement / 2;
        double yOffset = -1 * (pixelY * yIncrement + yIncrement / 2);

        Vector3 worldSpaceLocation = topLeftImagePlanePosition.addNew(new Vector3(xOffset, yOffset, 0)); // TODO: This assumes the viewing plane is always in the XY plane

        return worldSpaceLocation;
    }

    protected void initialize() {
        cameraPosition = world.getCamera().getPosition(); // Initialize camera position only once

        double imagePlaneLengthX = world.getCamera().getImagePlaneLengthX();
        double imagePlaneLengthY = world.getCamera().getImagePlaneLengthY();

        xIncrement = imagePlaneLengthX / settings.getResolutionX();
        yIncrement = imagePlaneLengthY / settings.getResolutionY();

        Vector3 imagePlaneCenter = world.getCamera().getImagePlaneCenter();

        topLeftImagePlanePosition = imagePlaneCenter.addNew(new Vector3(-imagePlaneLengthX / 2, imagePlaneLengthY / 2, 0));

        isInitialized = true;
    }


    abstract Color traceRay(Ray ray);
}
