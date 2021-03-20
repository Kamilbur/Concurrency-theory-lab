package lab09.notordered;

import org.jcsp.lang.Parallel;
import org.jcsp.lang.StandardChannelIntFactory;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.CSProcess;

public class Main {
    public static long timeStart;
    public static long timeEnd;
    public static final int numOfBuffers = 100;
    public static final int numOfItems = 10000;

    public static void main(String[] args) {
        StandardChannelIntFactory factory = new StandardChannelIntFactory();
        One2OneChannelInt[] channelsProd = factory.createOne2One(numOfBuffers);
        One2OneChannelInt[] channelsMore = factory.createOne2One(numOfBuffers);
        One2OneChannelInt[] channelsCons = factory.createOne2One(numOfBuffers);

        CSProcess[] procList = new CSProcess[numOfBuffers + 2];
        procList[0] = new Producer(channelsProd, channelsMore, numOfItems);
        procList[1] = new Consumer(channelsCons, numOfItems);

        for (int ii = 0; ii < numOfBuffers; ii++) {
            procList[ii + 2] = new Buffer(channelsProd[ii], channelsCons[ii], channelsMore[ii]);
        }

        Parallel par = new Parallel(procList);

        timeStart = System.currentTimeMillis();
        par.run();
    }
}