public class Main {
    public static void main(String[] args) {
        Object monitor = new Object();
        MyThread t1 = new MyThread(1, monitor);
        MyThread t2 = new MyThread(2, monitor);
        MyThread t3 = new MyThread(3, monitor);

        t1.start();
        t2.start();
        t3.start();
    }
}
