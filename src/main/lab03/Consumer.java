package lab03;

public class Consumer extends Thread {
    private static final FinishIndicator finishIndicator = FinishIndicator.getFinishIndicator();
    private final Buffer buff;
    private final int consumingSize;
    private int accessCount = 0;

    Consumer(int consumingSize, Buffer buff) {
        this.consumingSize = consumingSize;
        this.buff = buff;
    }

    @Override
    public void run() {
        do {
            try {
                this.buff.take(consumingSize);
                this.accessCount++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (finishIndicator.isNotFinished());
    }

    public int getConsumingSize() {
        return consumingSize;
    }

    public int getAccessCount() {
        return accessCount;
    }
}
