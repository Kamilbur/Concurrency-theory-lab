package lab08.javacode;

import java.util.ArrayList;

abstract class DiningRoom {
    private final ArrayList<Philosopher> philosophers = new ArrayList<>();
    private final ArrayList<Fork> forks = new ArrayList<>();
    final int N;

    DiningRoom(int N) {
        this.N = N;
        for (int ii = 0; ii < N; ii++) {
            Philosopher philosopher = new Philosopher(this, ii);
            this.philosophers.add(philosopher);
        }
        for (int ii = 0; ii < N; ii++) {
            this.forks.add(new Fork());
        }
    }

    void start() {
        for (Philosopher philosopher : this.philosophers) {
            philosopher.start();
        }
    }

    Fork getFork(int number) {
        return this.forks.get(number % this.N);
    }
}
