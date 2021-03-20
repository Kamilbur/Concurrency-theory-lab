package lab04;

public class Consumer extends Thread {
    private RunIndicator runIndicator;
    private final RandomGenerator random = new RandomGenerator();
    private boolean uniformProbability;
    private int maxConsumingSize;
    private boolean isFair;
    private Buffer buff;

    Consumer(int maxConsumingSize, RunIndicator runIndicator, Buffer buff, boolean uniformProbability, boolean isFair) {
        this.maxConsumingSize = maxConsumingSize;
        this.runIndicator = runIndicator;
        this.buff = buff;
        this.uniformProbability = uniformProbability;
        this.isFair = isFair;
    }

    @Override
    public void run() {
        do {
            int consumingSize;
            if (uniformProbability) {
                consumingSize = random.randomIntegerUniform(maxConsumingSize) + 1;
            }
            else {
                consumingSize = random.randomIntegerDecreasingProb(maxConsumingSize) + 1;
            }

            try {
                if (isFair) {
                    this.buff.takeFair(consumingSize);
                }
                else {
                    this.buff.take(consumingSize);
                }
            } catch (InterruptedException ex) {
                return;
            }
        } while (this.runIndicator.Should_Be_Running());
    }
}
