package lab03;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Main {
    private final static int thread_count = 8;

    public static void main(String[] args) {
        Buffer buff = new Buffer(1 << 8);
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        RunIndicator runIndicator = new RunIndicator();

        for (int ii = 0; ii < Main.thread_count; ii++) {
            Producer new_producer = new Producer(1 << ii, runIndicator, buff);
            Consumer new_consumer = new Consumer(1 << ii, runIndicator, buff);
            producers.add(new_producer);
            consumers.add(new_consumer);
            new_producer.start();
            new_consumer.start();
        }

        try {
            sleep( 10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            runIndicator.TurnOffThreads();
        }

        System.out.println("Summary:");
        for (Producer producer : producers) {
            System.out.println("lab03.Producer(" + producer.productionSize + "): " + producer.accessCount);
        }

        for (Consumer consumer : consumers) {
            System.out.println("lab03.Consumer(" + consumer.consumingSize + "): " + consumer.accessCount);
        }
    }
}
