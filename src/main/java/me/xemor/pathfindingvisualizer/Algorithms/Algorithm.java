package me.xemor.pathfindingvisualizer.Algorithms;

import me.xemor.pathfindingvisualizer.Grid.Grid;
import me.xemor.pathfindingvisualizer.Grid.GridView;

public abstract class Algorithm implements Runnable {

    protected Grid grid;
    protected GridView gridView;
    protected Thread thread = new Thread(this);
    private Speed speed;

    public Algorithm(GridView gridView, Speed speed) {
        this.grid = gridView.getGrid();
        this.gridView = gridView;
        this.speed = speed;
    }

    public void beginAlgorithm() {
        thread.start();
    }

    public abstract boolean nextStep();

    @Override
    public void run() {
        while (nextStep()) {
            try {
                Thread.sleep(speed.getMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
