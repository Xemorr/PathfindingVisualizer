package me.xemor.pathfindingvisualizer.MazeGeneration;

import me.xemor.pathfindingvisualizer.Grid.Grid;
import me.xemor.pathfindingvisualizer.Grid.GridView;


/**
 * The interface all MazeGenerators implement.
 */
public interface MazeGenerator {

    /**
     * The method that is called in order to start the maze generation.
     */
    void generate();

}
