package lab01;

public class SynchronizedCounter implements Counter {
    private int counter;

    public SynchronizedCounter() {
        this.counter = 0;
    }

    public synchronized void inc() {
        this.counter++;
    }

    public synchronized void dec() {
        this.counter--;
    }

    public synchronized String toString() {
        return String.valueOf(this.counter);
    }
}

