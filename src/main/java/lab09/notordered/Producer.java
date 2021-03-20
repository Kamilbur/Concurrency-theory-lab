package lab09.notordered;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private final One2OneChannelInt[] channelOut;
    private final One2OneChannelInt[] channelMore;
    private final int numOfItems;

    Producer(One2OneChannelInt[] out, One2OneChannelInt[] channelMore, int numOfItems) {
        this.channelOut = out;
        this.channelMore = channelMore;
        this.numOfItems = numOfItems;
    }

    @Override
    public void run() {
        final Guard[] guards = new Guard[channelMore.length];

        for (int ii = 0; ii < channelOut.length; ii++)
            guards[ii] = channelMore[ii].in();

        Alternative alt = new Alternative(guards);

        for (int ii = 0; ii < numOfItems; ii++) {
            int index = alt.select();
            channelMore[index].in().read();
            int item = (int) (Math.random() * 100) + 1;
            channelOut[index].out().write(item);
        }
    }
}
