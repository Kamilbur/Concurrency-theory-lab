package lab04;

public class Consumer extends Thread {
    private static final FinishIndicator finishIndicator = FinishIndicator.getFinishIndicator();
    private final RandomGenerator random = new RandomGenerator();
    private final boolean uniformProbability;
    private final int maxConsumingSize;
    private final boolean isFair;
    private final Buffer buff;

    Consumer(int maxConsumingSize, Buffer buff, boolean uniformProbability, boolean isFair) {
        this.maxConsumingSize = maxConsumingSize;
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
        } while (finishIndicator.isNotFinished());
    }
}
