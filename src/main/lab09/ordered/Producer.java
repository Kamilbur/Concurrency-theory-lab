package lab09.ordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Producer implements CSProcess {
    private final One2OneChannelInt channelOut;
    private final int numOfItems;
    
    Producer (final One2OneChannelInt out, final int numOfItems) {
        this.channelOut = out;
        this.numOfItems = numOfItems;
    }
    
    @Override
    public void run () {
        for (int ii = 0; ii < numOfItems; ii++) {
            int item = (int)(Math.random() * 100) + 1;
            channelOut.out().write(item);
        }
    }
}