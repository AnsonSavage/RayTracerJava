package algorithm.utils;

public class MathUtils {
    public static boolean isClose(double a, double b, double epsilon) {
        return Math.abs(a - b) < epsilon;
    }
    public static boolean isClose(double a, double b) {
        return isClose(a, b, 0.0001);
    }
    public static double distance(double a, double b) {
        return Math.abs(a - b);
    }
}
