package lab04;

public class Producer extends Thread {
    private static final FinishIndicator finishIndicator = FinishIndicator.getFinishIndicator();
    private final RandomGenerator random = new RandomGenerator();
    private final boolean uniformProbability;
    private final int maxProductionSize;
    private final boolean isFair;
    private final Buffer buff;

    Producer(int maxProductionSize, Buffer buff, boolean uniformProbability, boolean isFair) {
        this.maxProductionSize = maxProductionSize;
        this.uniformProbability = uniformProbability;
        this.buff = buff;
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

        } while (finishIndicator.isNotFinished());
    }
}