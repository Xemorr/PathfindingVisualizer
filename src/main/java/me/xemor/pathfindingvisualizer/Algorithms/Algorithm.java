package me.xemor.pathfindingvisualizer.Algorithms;

import me.xemor.pathfindingvisualizer.Grid.Grid;
import me.xemor.pathfindingvisualizer.Grid.GridView;

/**
 * This class is the base class for all of my other algorithms and implements runnable in order for it
 * to be able to be ran from a different thread.
 */
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

    /**
     * Called from the start button in order to start the run() method in a different thread.
     */
    public void beginAlgorithm() {
        thread.start();
    }

    /**
     * The method that each individual algorithm overrides,
     * this represents each iterative step of the algorithms for visualisation.
     * @return a boolean representing whether the algorithm is completed or not.
     */
    public abstract boolean nextStep();

    /**
     * This method executes the nextStep() method iteratively in configurable time steps.
     */
    @Override
    public void run() {
        while (nextStep()) {
            try {
                Thread.sleep(speed.getMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        grid.removeTracking();
        gridView.postInvalidate();
    }
}
