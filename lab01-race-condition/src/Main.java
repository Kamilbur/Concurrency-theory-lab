public class Main {
    public static final boolean synchronization = true;

    public static void main(String[] args) {
        Counter c;
        if (synchronization) {
            c = new SynchronizedCounter();
        }
        else {
            c = new NotSynchronizedCounter();
        }
        MyThread t1 = new MyThread(c, 0);
        MyThread t2 = new MyThread(c, 1);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(c.toString());
    }
}
