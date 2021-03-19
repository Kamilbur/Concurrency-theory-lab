package lab03;

class RunIndicator {
    private boolean should_run;

    RunIndicator() {
        this.should_run = true;
    }

    void TurnOffThreads() {
        this.should_run = false;
    }

    boolean Should_Be_Running() {
        return this.should_run;
    }
}
