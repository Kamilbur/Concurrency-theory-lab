public class MyThread extends Thread {
    private final int number;
    private final Object monitor;
    private static int n = 0;
    private static int numOfThreads = 0;

    MyThread(int number, Object monitor) {
        this.number = number;
        this.monitor = monitor;
        MyThread.numOfThreads += 1;
    }

    public void run() {
        for (;;) {
            synchronized (this.monitor) {
                while (MyThread.n + 1 != this.number) {
                    try {
                        this.monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(this.number);

                MyThread.n = (MyThread.n + 1) % MyThread.numOfThreads;
                this.monitor.notifyAll();
            }
        }
    }
}
