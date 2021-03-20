package lab09.notordered;

import org.jcsp.lang.Alternative;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Guard;
import org.jcsp.lang.One2OneChannelInt;

public class Consumer implements CSProcess {
    private final One2OneChannelInt[] channelIn;
    private final int numOfItems;

    Consumer(One2OneChannelInt[] channelIn, int numOfItems) {
        this.channelIn = channelIn;
        this.numOfItems = numOfItems;
    }

    @Override
    public void run() {
        Guard[] guards = new Guard[channelIn.length];

        for (int ii = 0; ii < channelIn.length; ii++)
            guards[ii] = channelIn[ii].in();
        Alternative alt = new Alternative(guards);
        for (int ii = 0; ii < numOfItems; ii++) {
            int index = alt.select();
            int item = channelIn[index].in().read();

            // Here is a hypothetical consumption
        }

        Main.timeEnd = System.currentTimeMillis();
        System.out.println("Time: " + (Main.timeEnd - Main.timeStart) + " ms");

        System.exit(0); // terminate the whole program
    }
}
