package lab03;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Buffer {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull  = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final int buff_size;
    private int count;

    Buffer(int buff_size) {
        this.count = 0;
        this.buff_size = buff_size;
    }

    void put(int input_size) throws InterruptedException {
        lock.lock();
        try {
            while (count + input_size > buff_size)
                notFull.await();
            count += input_size;
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
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
    }
}