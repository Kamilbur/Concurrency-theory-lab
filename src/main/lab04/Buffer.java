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
    private final int buffSize;
    private int count;
    final CounterMap<Integer> producerAccessCount = new CounterMap<>();
    final CounterMap<Integer> consumerAccessCount = new CounterMap<>();

    Buffer(int buffSize) {
        this.count = 0;
        this.buffSize = buffSize;
    }

    void putFair(int inputSize) throws InterruptedException {
        lock.lock();
        try {
            if (lock.hasWaiters(firstProd)) restProd.await();
            while (count + inputSize > buffSize)
                firstProd.await();
            count += inputSize;

            producerAccessCount.increment(inputSize);

            restProd.signal();
            firstCons.signal();
        } finally {
            lock.unlock();
        }
    }

    void takeFair(int outputSize) throws InterruptedException {
        lock.lock();
        try {
            if (lock.hasWaiters(firstCons)) restCons.await();
            while (count - outputSize < 0)
                firstCons.await();
            count -= outputSize;

            consumerAccessCount.increment(outputSize);

            restCons.signal();
            firstProd.signal();
        } finally {
            lock.unlock();
        }
    }

    void put(int inputSize) throws InterruptedException {
        lock.lock();
        try {
            while (count + inputSize > buffSize)
                notFull.await();
            count += inputSize;

            producerAccessCount.increment(inputSize);

            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    void take(int outputSize) throws InterruptedException {
        lock.lock();
        try {
            while (count - outputSize < 0)
                notEmpty.await();
            count -= outputSize;
            consumerAccessCount.increment(outputSize);
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }
}