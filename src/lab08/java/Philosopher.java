package lab08.java;

class Philosopher extends Thread {
    private final DiningRoom room;
    private Fork left;
    private Fork right;
    private final int chairNumber;

    Philosopher(DiningRoom room, int chairNumber) {
        this.room = room;
        this.chairNumber = chairNumber;
    }

    private void think() {
        System.out.println("Philosopher " + this.chairNumber + " started thinking");
        try {
            Thread.sleep((long)(Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        left = room.getFork(chairNumber);
        right = room.getFork(chairNumber + 1);
        for (;;) {
            think();
            try {
                if (this.room instanceof DiningRoomStarvation) {
                    eatStarve();
                }
                if (this.room instanceof DiningRoomArbiter) {
                    eatArbiter();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void eatStarve() throws InterruptedException {
        System.out.println("Philosopher " + this.chairNumber + " is hungry");
        /* Pick forks */
        DiningRoomStarvation room = (DiningRoomStarvation) this.room;

        room.update.lock();
        System.out.println("Philosopher " + this.chairNumber + " started waiting for forks");
        if ( left.taken || right.taken) {
            System.out.println("Philosopher " + this.chairNumber + " cannot acquire two forks");
            room.update.unlock();
            return;
        }

        System.out.println("Philosopher " + this.chairNumber + " acquired forks");

        left.take();
        right.take();

        room.update.unlock();


        /* Critical section */
        System.out.println("Philosopher " + this.chairNumber + " eating");
        Thread.sleep((long)(Math.random() * 1000));

        /* Release forks */
        room.update.lock();
        System.out.println("Philosopher " + this.chairNumber + " put away forks");
        left.putAway();
        right.putAway();

        room.update.unlock();
    }

    private void eatArbiter() throws InterruptedException {
        System.out.println("Philosopher " + this.chairNumber + " is hungry");
        /* Pick forks */

        DiningRoomArbiter room = (DiningRoomArbiter) this.room;

        room.arbiter.acquire();
        System.out.println("Philosopher " + this.chairNumber + " in the dining room");

        left.take();
        right.take();
        System.out.println("Philosopher " + this.chairNumber + " took both forks");


        /* Critical section */
        System.out.println("Philosopher " + this.chairNumber + " eating");
        Thread.sleep((long)(Math.random() * 1000));

        /* Release forks */
        left.putAway();
        right.putAway();

        room.arbiter.release();
        System.out.println("Philosopher " + this.chairNumber + " is out of dining room");
    }
}
