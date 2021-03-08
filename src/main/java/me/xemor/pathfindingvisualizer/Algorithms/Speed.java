package me.xemor.pathfindingvisualizer.Algorithms;

public enum Speed {
    FAST(62), NORMAL(250), SLOW(1000);

    private final long millis;

    Speed(long millis) {
        this.millis = millis;
    }

    /**
     * The length to wait in between steps for each speed.
     * @return
     */
    public long getMillis() {
        return millis;
    }
}
