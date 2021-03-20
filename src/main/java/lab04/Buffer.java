package lab04;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Buffer {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition firstProd  = lock.newCondition();
    private final Condition restProd = lock.newCondition();
    private final Condition firstCons  = lock.newCondition();
    private final Condition restCons = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private int count;
    private int buff_size;

    Buffer(int buff_size) {
        this.count = 0;
        this.buff_size = buff_size;
    }

    void putFair(int input_size) throws InterruptedException {
        lock.lock();
        try {
            if (lock.hasWaiters(firstProd)) restProd.await();
            while (count + input_size > buff_size)
                firstProd.await();
            count += input_size;
            Main.producerAccessCount.put(input_size, Main.producerAccessCount.getOrDefault(input_size, 0) + 1);
            restProd.signal();
            firstCons.signal();
        } finally {
            lock.unlock();
        }
    }

    void takeFair(int output_size) throws InterruptedException {
        lock.lock();
        try {
            if (lock.hasWaiters(firstCons)) restCons.await();
            while (count - output_size < 0)
                firstCons.await();
            count -= output_size;
            Main.consumerAccessCount.put(output_size, Main.consumerAccessCount.getOrDefault(output_size, 0) + 1);
            restCons.signal();
            firstProd.signal();
        } finally {
            lock.unlock();
        }
    }

    void put(int input_size) throws InterruptedException {
        lock.lock();
        try {
            while (count + input_size > buff_size)
                notFull.await();
            count += input_size;
            Main.producerAccessCount.put(input_size, Main.producerAccessCount.getOrDefault(input_size, 0) + 1);
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    void take(int output_size) throws InterruptedException {
        lock.lock();
        try {
            while (count - output_size < 0)
                notEmpty.await();
            count -= output_size;
            Main.consumerAccessCount.put(output_size, Main.consumerAccessCount.getOrDefault(output_size, 0) + 1);
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }
}