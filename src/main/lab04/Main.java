package lab04;

import java.util.*;

import static java.lang.Thread.sleep;

public class Main {
    private static final int TIME_SEC = 10;
    private static final int M = 10;
    private static final int THREAD_COUNT = 5;
    private static final boolean HAS_UNIFORM_PROB = true;
    private static final boolean IS_FAIR = false;
    private static final FinishIndicator finishIndicator = FinishIndicator.getFinishIndicator();

    public static void main(String[] args) {
        Buffer buff = new Buffer(M);
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();

        for (int ii = 0; ii < THREAD_COUNT; ii++) {
            Producer new_producer = new Producer(M / 2, buff, HAS_UNIFORM_PROB, IS_FAIR);
            Consumer new_consumer = new Consumer(M / 2, buff, HAS_UNIFORM_PROB, IS_FAIR);
            producers.add(new_producer);
            consumers.add(new_consumer);
            new_producer.start();
            new_consumer.start();
        }

        System.out.println("Threads initialized");

        try {
            sleep( TIME_SEC * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            finishIndicator.finish();
        }
        /*
         * It is possible that all producers will stop and consumers will be locked forever.
         * This code is for algorithm testing purposes only, so those threads are stopped with interrupt.
         */
        producers.forEach(Thread::interrupt);
        consumers.forEach(Thread::interrupt);

        for (Producer producer: producers) {
            try {
                producer.join();
            } catch (InterruptedException ignored) {}
        }

        for (Consumer consumer: consumers) {
            try {
                consumer.join();
            } catch (InterruptedException ignored) {}
        }

        System.out.println("Threads done");
        System.out.println("Producers");
        for (Map.Entry<Integer, Integer> entry : buff.producerAccessCount.entrySet()) {
            System.out.println("Producer(" + entry.getKey() + "): " + entry.getValue());
        }

        System.out.println("Consumers");
        for (Map.Entry<Integer, Integer> entry : buff.consumerAccessCount.entrySet()) {
            System.out.println("Consumer(" + entry.getKey() + "): " + entry.getValue());
        }
    }
}
