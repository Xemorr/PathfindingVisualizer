package me.xemor.pathfindingvisualizer.Algorithms.NoHeuristic;

import java.util.List;

import me.xemor.pathfindingvisualizer.Algorithms.Speed;
import me.xemor.pathfindingvisualizer.Grid.GridView;
import me.xemor.pathfindingvisualizer.Grid.Tile;

public class BreadthFirstAlgorithm extends DepthFirstAlgorithm {

    public BreadthFirstAlgorithm(GridView gridView, Speed speed) {
        super(gridView, speed);
    }

    @Override
    public boolean nextStep() {
        NoWeightingNode node = stackSearch.removeFirst();
        if (!visitedNodeOrder.contains(node)) {
            visitedNodeOrder.add(node);
            grid.setTile(node.getNode().getX(), node.getNode().getY(), Tile.CHECKED);
            gridView.postInvalidate();
            List<NoWeightingNode> neighbours = node.getNeighbours(noWeightingGrid);
            neighbours.stream()
                    .filter(theNode -> grid.getTile(theNode.getNode()) != Tile.OBSTACLE && !visitedNodeOrder.contains(theNode))
                    .forEach(theNode -> {
                        stackSearch.addLast(theNode);
                        theNode.setParentNode(node);
                    });
            if (node.equals(noWeightingGrid.getEndNode())) {
                reconstructPath(node); return false;
            }
        }
        return !stackSearch.isEmpty();
    }
}
