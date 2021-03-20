package lab05;

import java.io.IOException;

public class Main {
    private static final int NANO_IN_MIL = 1000 * 1000;
    private static final int NUM_OF_THREADS = 100;
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int MAX_ITER = 1500;
    private static final double ZOOM = 300;

    public static void main(String[] args) {
        Mandelbrot mandelbrotFrame = new Mandelbrot(WIDTH, HEIGHT, MAX_ITER, ZOOM, NUM_OF_THREADS);
        long startTime = System.nanoTime();
        mandelbrotFrame.computeAndShow();
        long endTime = System.nanoTime();
        long timeElapsed = (endTime - startTime) / NANO_IN_MIL;
        System.out.println(timeElapsed + " ms");

        try {
            mandelbrotFrame.save("result", "png");
        } catch (IOException ignored) {
            System.out.println("Unable to save frame to file");
        }
    }
}
