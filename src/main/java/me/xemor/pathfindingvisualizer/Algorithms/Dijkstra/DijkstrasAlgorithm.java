package me.xemor.pathfindingvisualizer.Algorithms.Dijkstra;

import me.xemor.pathfindingvisualizer.Algorithms.Algorithm;
import me.xemor.pathfindingvisualizer.Algorithms.Speed;
import me.xemor.pathfindingvisualizer.Grid.GridView;

public class DijkstrasAlgorithm extends Algorithm {

    public DijkstrasAlgorithm(GridView gridView, Speed speed) {
        super(gridView, speed);
    }

    @Override
    public boolean nextStep() {
        return false;
    }

}
