package lab03;

public class Consumer extends Thread {
    private RunIndicator runIndicator;
    private Buffer buff;
    int consumingSize;
    int accessCount = 0;

    Consumer(int consumingSize, RunIndicator runIndicator, Buffer buff) {
        this.consumingSize = consumingSize;
        this.runIndicator = runIndicator;
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
        } while (this.runIndicator.Should_Be_Running());
    }
}
