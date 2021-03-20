package lab09.ordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer implements CSProcess {
    private final One2OneChannelInt channelIn;
    private final One2OneChannelInt channelOut;

    Buffer(One2OneChannelInt channelIn, One2OneChannelInt channelOut) {
        this.channelOut = channelOut;
        this.channelIn = channelIn;
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        for (;;) {
            channelOut.out().write(channelIn.in().read());
        }
    }
}