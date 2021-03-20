package lab05;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {
    private final int width;
    private final int height;
    private final int centerX;
    private final int centerY;
    private final int maxIter;
    private final double zoom;
    private final BufferedImage bufferedImage;
    private final int numOfThreads;
    private final ThreadPoolExecutor executor;

    Mandelbrot(int width, int height, int maxIter, double zoom, int numOfThreads) {
        // Frame settings
        super("Mandelbrot Set");
        setBounds(100, 100, width, height);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // normal constructor
        this.numOfThreads = numOfThreads;
        this.width = width;
        this.height = height;
        this.centerX = (int) (0.7 * width);
        this.centerY = height / 2;
        this.maxIter = maxIter;
        this.zoom = zoom;
        this.bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numOfThreads);
    }

    void computeAndShow() {
        ArrayList<Worker> workers = new ArrayList<>();

        for (int ii = 0; ii < numOfThreads; ii++) {
            int startRow = ii * height / numOfThreads;
            int endRow = (ii + 1) * height / numOfThreads;
            workers.add(new Worker(startRow, endRow, width, centerX, centerY, maxIter, zoom));
        }

        try {
            List<Future<List<int[]>>> futures = executor.invokeAll(workers);
            for (Future<List<int[]>> future : futures) {
                List<int[]> result = future.get();
                for (int[] triplet : result) {
                    int x = triplet[0];
                    int y = triplet[1];
                    int iter = triplet[2];
                    bufferedImage.setRGB(x, y, iter | (iter << 8));
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, 0, this);
    }

    public void save(String filename, String formatName) throws IOException {
        File output = new File(filename + "." + formatName);
        ImageIO.write(bufferedImage, formatName, output);
    }
}