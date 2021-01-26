package me.xemor.pathfindingvisualizer.Algorithms;

public enum Speed {
    FAST(62), NORMAL(250), SLOW(1000);

    private long millis;

    Speed(long millis) {
        this.millis = millis;
    }

    public long getMillis() {
        return millis;
    }
}
