package lab08.javacode;

import java.util.concurrent.Semaphore;

class Fork extends Semaphore {
    boolean taken = false;

    Fork() {
        super(1);
    }

    void take() throws InterruptedException {
        this.taken = true;
        this.acquire();
    }

    void putAway() {
        this.taken = false;
        this.release();
    }
}
