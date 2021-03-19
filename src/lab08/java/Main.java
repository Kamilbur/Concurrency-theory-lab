package lab08.java;

public class Main {
    static int numOfThreads = 5;

    public static void main(String[] args) {
        //DiningRoom diningRoom = new DiningRoomStarvation(numOfThreads);
        DiningRoom diningRoom = new DiningRoomArbiter(numOfThreads);
        diningRoom.start();
    }
}
