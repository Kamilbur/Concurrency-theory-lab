package lab01;

public class MyThread extends Thread {
    private final Counter counter;
    private final int type;

    MyThread(Counter counter, int inc_dec) {
        this.counter = counter;
        this.type = inc_dec;
    }

    public void run() {
        for (int ii = 0; ii < 1e8; ii++) {
            if (this.type % 2 == 0) {
                this.counter.inc();
            }
            else {
                this.counter.dec();
            }
        }
    }
}
