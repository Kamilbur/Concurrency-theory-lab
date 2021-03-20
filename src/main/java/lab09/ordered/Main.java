package lab09.ordered;

import org.jcsp.lang.Parallel;
import org.jcsp.lang.StandardChannelIntFactory;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.CSProcess;

public class Main {
    public static long timeStart;
    public static long timeEnd;
    public static final int N = 100;  // num of buffers
    public static final int numOfItems = 10000;

    public static void main(String[] args) {

        StandardChannelIntFactory factory = new StandardChannelIntFactory();
        One2OneChannelInt[] channels = factory.createOne2One(N + 1);

        CSProcess[] procList = new CSProcess[N + 2];
        procList[0] = new Producer(channels[0], numOfItems);
        procList[1] = new Consumer(channels[N], numOfItems);

        for (int ii = 0; ii < N; ii++) {
            procList[ii + 2] = new Buffer(channels[ii], channels[ii + 1]);
        }

        Parallel par = new Parallel(procList);

        timeStart = System.currentTimeMillis();
        par.run();
    }
}