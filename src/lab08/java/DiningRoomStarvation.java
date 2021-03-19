package lab08.java;

import java.util.concurrent.locks.ReentrantLock;

public class DiningRoomStarvation extends DiningRoom {
    ReentrantLock update = new ReentrantLock();

    DiningRoomStarvation(int N) {
        super(N);
    }
}
