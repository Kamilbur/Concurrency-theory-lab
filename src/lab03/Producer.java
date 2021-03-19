package lab03;

public class Producer extends Thread {
    private final RunIndicator runIndicator;
    private final Buffer buff;
    int productionSize;
    int accessCount = 0;
    

    Producer(int productionSize, RunIndicator runIndicator, Buffer buff) {
        this.productionSize = productionSize;
        this.runIndicator = runIndicator;
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

        } while (this.runIndicator.Should_Be_Running());
    }
}
