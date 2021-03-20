package lab09.ordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private final One2OneChannelInt channelIn;
    private final int numOfItems;

    Consumer(final One2OneChannelInt channelIn, final int numOfItems) {
        this.channelIn = channelIn;
        this.numOfItems = numOfItems;
    }

    @Override
    public void run() {
        for (int ii = 0; ii < numOfItems; ii++) {
            int item = channelIn.in().read();

            // Here is a hypothetical consumption
        }

        Main.timeEnd = System.currentTimeMillis();
        System.out.println("Time: " + (Main.timeEnd - Main.timeStart) + " ms");

        System.exit(0);  // terminate the whole program
    }
}