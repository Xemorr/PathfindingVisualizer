package me.xemor.pathfindingvisualizer.Algorithms.Dijkstra;

import me.xemor.pathfindingvisualizer.Algorithms.AStar.AStarAlgorithm;
import me.xemor.pathfindingvisualizer.Algorithms.Speed;
import me.xemor.pathfindingvisualizer.Grid.GridView;

public class DijkstraAStarAlgorithm extends AStarAlgorithm {
    public DijkstraAStarAlgorithm(GridView gridView, Speed speed) {
        super(gridView, speed, 0);
    }
}
