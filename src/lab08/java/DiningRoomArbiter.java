package lab08.java;

import java.util.concurrent.Semaphore;

public class DiningRoomArbiter extends DiningRoom {
    public Semaphore arbiter;

    DiningRoomArbiter(int N) {
        super(N);
        arbiter = new Semaphore(N - 1);
    }
}
