public class IndexOfRefractionSteps {
    public static void main(String[] args) {
        double startValue = 1.0;
        double endValue = 3.5;
        int numSteps = 40;
//        double[] indexOfRefraction = nonLinearSteps(startValue, endValue, numSteps);

//        for (double refractionIndex : indexOfRefraction) {
//            System.out.println(refractionIndex);
//        }
    }

    public static double[] calculateIndices(double startValue, double endValue, int numSteps) {
        double[] indices = new double[numSteps];
        double range = endValue - startValue;
        double totalSum = 0;

        // Generate non-linear scale values
        for (int i = 0; i < numSteps; i++) {
            double nonLinearScale = Math.pow((double) i / (numSteps - 1), 2);
            totalSum += nonLinearScale;
        }

        // Calculate the actual increments based on the non-linear scale
        double sumOfIncrements = 0;
        for (int i = 0; i < numSteps; i++) {
            double nonLinearScale = Math.pow((double) i / (numSteps - 1), 2);
            double increment = (nonLinearScale / totalSum) * range;
            sumOfIncrements += increment;
            indices[i] = startValue + sumOfIncrements;
        }

        // Ensure the last value is exactly the endValue
        indices[numSteps - 1] = endValue;

        return indices;
    }
}
