package lab04;

import java.util.Random;

class RandomGenerator extends Thread {
    private final Random rand = new Random();

    int randomIntegerDecreasingProb(int max) {
        /*
         * Random integer between 0 and max (max exclusive) with probability inversely proportional to integer value.
         */

        double x = rand.nextDouble();
        double shift = 1 / Math.log(max + Math.E);
        x = shift + (1 - shift) * x;
        return (int) (Math.exp(1 / x) - Math.E);
    }

    int randomIntegerUniform(int max) {
        /*
         * Random int between 0 and max (max exclusive).
         */

        return Math.abs(rand.nextInt()) % max;
    }
}
