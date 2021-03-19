package lab01;

public class NotSynchronizedCounter implements Counter {
    private int counter;

    public NotSynchronizedCounter() {
        this.counter = 0;
    }

    public void inc() {
        this.counter++;
    }

    public void dec() {
        this.counter--;
    }

    public synchronized String toString() {
        return String.valueOf(this.counter);
    }
}
