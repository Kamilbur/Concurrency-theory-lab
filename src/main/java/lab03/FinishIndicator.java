package lab03;

public class FinishIndicator {
    private static boolean finished;
    private static FinishIndicator object = null;

    private FinishIndicator() {
        finished = false;
    }

    public boolean isNotFinished() {
        return ! finished;
    }

    public void finish() {
        finished = true;
    }

    public static FinishIndicator getFinishIndicator() {
        if (object == null) {
            object = new FinishIndicator();
        }
        return object;
    }
}
