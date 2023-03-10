package algorithm;

import utilities.Color;
import utilities.Ray;
import utilities.Vector3;
import world.World;

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
        int totalPixels = settings.getResolutionX() * settings.getResolutionY();
        for (int i = 0; i < settings.getResolutionX(); i++) {
            for (int j = 0; j < settings.getResolutionY(); j++) {
                Ray ray = getRayDirection(i, j);
//                Ray ray = getRayDirection(162, 427);
                System.out.println("Progress: " + (i * settings.getResolutionY() + j) / (double) totalPixels * 100 + "%");
                Color color = traceRay(ray);
                image.setPixel(i, j, color);
            }
        }
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
