package lab09.notordered;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.One2OneChannelInt;

public class Buffer implements CSProcess {
    private final One2OneChannelInt channelIn;
    private final One2OneChannelInt channelOut;
    private final One2OneChannelInt channelMore;

    Buffer(One2OneChannelInt in, One2OneChannelInt out, One2OneChannelInt channelMore) {
        this.channelOut = out;
        this.channelIn = in;
        this.channelMore = channelMore;
    }

    @Override
    public void run() {
        for (;;) {
            channelMore.out().write(0);
            channelOut.out().write(channelIn.in().read());
        }
    }
}