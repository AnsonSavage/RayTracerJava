package algorithm;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.World;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class RayTracer extends RenderAlgorithm {
    protected boolean isInitialized = false;
    protected Vector3 topLeftImagePlanePosition;
    protected Vector3 cameraPosition;
    private double xIncrement;
    private double yIncrement;

    public RayTracer(RenderSettings settings, World world) {
        super(settings, world);
    }

    @Override
    protected void renderImplementation() {
        multithreadedRenderImplementation();
//        double startTime = System.currentTimeMillis();
//        int totalPixels = settings.getResolutionX() * settings.getResolutionY();
//        for (int i = 0; i < settings.getResolutionX(); i++) {
//            for (int j = 0; j < settings.getResolutionY(); j++) {
//                Ray ray = getRayDirection(i, j);
//                System.out.println("Progress: " + (i * settings.getResolutionY() + j) / (double) totalPixels * 100 + "%");
//                Color color = traceRay(ray);
//                image.setPixel(i, j, color);
//            }
//        }
//        System.out.println("Rendering completed in " + (System.currentTimeMillis() - startTime) + " milliseconds");
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
                for (int i = 0; i < settings.getResolutionX(); i++) {
                    for (int j = start; j < end; j++) {
                        Ray ray = getRayDirection(i, j);
                        Color color = traceRay(ray);
                        image.setPixel(i, j, color);
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

        System.out.println("Rendering completed in " + (System.currentTimeMillis() - startTime) + " milliseconds, using " + numThreads + " threads");

    }

    protected Ray getRayDirection(int pixelX, int pixelY) {
        if (!isInitialized) {
            initialize();
        }
        double xOffset = pixelX * xIncrement + xIncrement / 2;
        double yOffset = -1 * (pixelY * yIncrement + yIncrement / 2);

        Vector3 pixelRayEnd = topLeftImagePlanePosition.addNew(new Vector3(xOffset, yOffset, 0));

        Vector3 rayDirection = pixelRayEnd.subtractNew(cameraPosition);
        rayDirection.normalize(); // Normalize ray direction for simplicity of code when testing sphere intersection

        return new Ray(cameraPosition, rayDirection);
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
