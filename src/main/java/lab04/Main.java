package lab04;

import java.util.*;

import static java.lang.Thread.sleep;

public class Main {
    private static final int TIME_SEC = 10;
    private static final int M = 100 * 1000;
    private static final int THREAD_COUNT = 100;
    private static final boolean HAS_UNIFORM_PROB = false;
    private static final boolean IS_FAIR = false;
    static HashMap<Integer, Integer> producerAccessCount = new HashMap<>();
    static HashMap<Integer, Integer> consumerAccessCount = new HashMap<>();

    public static void main(String[] args) {
        Buffer buff = new Buffer(M);
        ArrayList<Producer> producers = new ArrayList<>();
        ArrayList<Consumer> consumers = new ArrayList<>();
        RunIndicator runIndicator = new RunIndicator();

        for (int ii = 0; ii < Main.THREAD_COUNT; ii++) {
            Producer new_producer = new Producer(M / 2, runIndicator, buff, HAS_UNIFORM_PROB, IS_FAIR);
            Consumer new_consumer = new Consumer(M / 2, runIndicator, buff, HAS_UNIFORM_PROB, IS_FAIR);
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
            runIndicator.TurnOffThreads();
        }
        /*
         * It is possible that at some moment all threads of one type will stop.
         */
        producers.forEach(Thread::interrupt);
        consumers.forEach(Thread::interrupt);

        System.out.println("Threads done");
        System.out.println("Producers");
        for (HashMap.Entry<Integer, Integer> entry : producerAccessCount.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("Consumers");
        for (HashMap.Entry<Integer, Integer> entry : consumerAccessCount.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
