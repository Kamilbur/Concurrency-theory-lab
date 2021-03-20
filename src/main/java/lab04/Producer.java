package lab04;

public class Producer extends Thread {
    private RunIndicator runIndicator;
    private final RandomGenerator random = new RandomGenerator();
    private boolean uniformProbability;
    private int maxProductionSize;
    private boolean isFair;
    private Buffer buff;

    Producer(int maxProductionSize, RunIndicator runIndicator, Buffer buff, boolean uniformProbability, boolean isFair) {
        this.maxProductionSize = maxProductionSize;
        this.runIndicator = runIndicator;
        this.buff = buff;
        this.uniformProbability = uniformProbability;
        this.isFair = isFair;
    }

    @Override
    public void run() {
        do {
            int productionSize;
            if (uniformProbability) {
                productionSize = random.randomIntegerUniform(maxProductionSize) + 1;
            }
            else {
                productionSize = random.randomIntegerDecreasingProb(maxProductionSize) + 1;
            }
            try {
                if (isFair) {
                    this.buff.putFair(productionSize);
                }
                else {
                    this.buff.put(productionSize);
                }
            } catch (InterruptedException e) {
                return;
            }

        } while (this.runIndicator.Should_Be_Running());
    }
}
