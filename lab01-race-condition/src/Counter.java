public interface Counter {
    /**
     * Increments counter.
     * Implementation can ensure (or not) synchronization.
     */
    void inc();

    /**
     * Decrements counter.
     * Implementation can ensure (or not) synchronization.
     */
    void dec();
}
