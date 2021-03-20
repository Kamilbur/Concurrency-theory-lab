package lab03;

public class Producer extends Thread {
    private static final FinishIndicator finishIndicator = FinishIndicator.getFinishIndicator();
    private final Buffer buff;
    int productionSize;
    int accessCount = 0;
    

    Producer(int productionSize, Buffer buff) {
        this.productionSize = productionSize;
        this.buff = buff;
    }

    @Override
    public void run() {
        do {
            try {
                this.buff.put(productionSize);
                this.accessCount++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (finishIndicator.isNotFinished());
    }

    public int getProductionSize() {
        return productionSize;
    }

    public int getAccessCount() {
        return accessCount;
    }
}
