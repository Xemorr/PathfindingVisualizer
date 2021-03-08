package me.xemor.pathfindingvisualizer.MazeGeneration;

import java.util.concurrent.ThreadLocalRandom;

import me.xemor.pathfindingvisualizer.Grid.Grid;
import me.xemor.pathfindingvisualizer.Grid.GridView;
import me.xemor.pathfindingvisualizer.Grid.Tile;

/**
 * Randomizes on a per-tile basis whether to put an obstacle there.
 * Does not necessarily produce a path.
 */
public class SimpleRandomMazeGenerator implements MazeGenerator {

    GridView gridView;
    public SimpleRandomMazeGenerator(GridView gridView) {
        this.gridView = gridView;
    }

    /**
     * Implementation of the generate method in the MazeGenerator interface
     * This method iterates over all tiles in the grid and has a 50/50 chance of
     * setting it to an obstacle or a tile.
     */
    @Override
    public void generate() {
        Grid grid = gridView.getGrid();
        for (int i = 0; i < grid.getColumns(); i++) {
            for (int j = 0; j < grid.getRows(); j++) {
                Tile currentTile = grid.getTile(i, j);
                if (currentTile == Tile.START || currentTile == Tile.END) {
                    continue;
                }
                boolean placeObstacle = ThreadLocalRandom.current().nextBoolean();
                if (placeObstacle) {
                    grid.setTile(i, j, Tile.OBSTACLE);
                } else {
                    grid.setTile(i, j, Tile.EMPTY);
                }
            }
        }
        gridView.postInvalidate();
    }
}
