package lab05;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class Worker implements Callable<List<int[]>> {
    private final int startRow;
    private final int endRow;
    private final int numOfCols;
    private final int centerX;
    private final int centerY;
    private final int maxIter;
    private final double zoom;

    Worker(int startRow, int endRow, int numOfCols, int centerX, int centerY, int maxIter, double zoom) {
        this.startRow = startRow;
        this.endRow = endRow;
        this.numOfCols = numOfCols;
        this.centerX = centerX;
        this.centerY = centerY;
        this.maxIter = maxIter;
        this.zoom = zoom;
    }

    @Override
    public List<int[]> call() {
        List<int[]> result = new LinkedList<>();
        for (int y = startRow; y < endRow; y++) {
            for (int x = 0; x < numOfCols; x++) {
                // start with z = rez + i * imz
                double rez, imz;
                rez = 0;
                imz = 0;
                double cX = (x - centerX) / zoom;
                double cY = (y - centerY) / zoom;
                int iter = maxIter;
                // iterate function f(z) = z^2 + c iter times
                while (rez * rez + imz * imz < 4 && iter > 0) {
                    double tmp = rez * rez - imz * imz + cX;
                    imz = 2.0 * rez * imz + cY;
                    rez = tmp;
                    iter--;
                }
                result.add(new int[]{x, y, iter});
            }
        }
        return result;
    }
}
