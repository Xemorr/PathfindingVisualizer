package me.xemor.pathfindingvisualizer.Algorithms.NoHeuristic;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;

import me.xemor.pathfindingvisualizer.Algorithms.Algorithm;
import me.xemor.pathfindingvisualizer.Algorithms.Speed;
import me.xemor.pathfindingvisualizer.Grid.GridView;
import me.xemor.pathfindingvisualizer.Grid.Tile;

public class DepthFirstAlgorithm extends Algorithm {

    protected Deque<NoWeightingNode> stackSearch = new ArrayDeque<>();
    protected HashSet<NoWeightingNode> visitedNodeOrder = new HashSet<>();
    protected NoWeightingGrid noWeightingGrid;

    public DepthFirstAlgorithm(GridView gridView, Speed speed) {
        super(gridView, speed);
        noWeightingGrid = new NoWeightingGrid(gridView.getGrid());
        stackSearch.push(noWeightingGrid.getStartNode());
    }

    /**
     * This method draws the optimal path onto the grid, once it has been formed.
     * @param currentNode
     */
    public void reconstructPath(NoWeightingNode currentNode) {
        while (true) {
            currentNode = currentNode.getParentNode();
            if (currentNode.equals(noWeightingGrid.getStartNode())) {
                break;
            }
            else {
                grid.setTile(currentNode.getNode().getX(), currentNode.getNode().getY(), Tile.ROUTE);
            }
        }
    }

    /**
     * This method defines each individual step of this iterative DepthFirst algorithm implementation
     * @return A boolean representing whether the method has hit the end node or not.
     */
    @Override
    public boolean nextStep() {
        NoWeightingNode node = stackSearch.pop();
        if (!visitedNodeOrder.contains(node)) {
            visitedNodeOrder.add(node);
            grid.setTile(node.getNode().getX(), node.getNode().getY(), Tile.CHECKED);
            gridView.postInvalidate();
            List<NoWeightingNode> neighbours = node.getNeighbours(noWeightingGrid);
            neighbours.stream()
                    .filter(theNode -> grid.getTile(theNode.getNode()) != Tile.OBSTACLE && !visitedNodeOrder.contains(theNode))
                    .forEach(theNode -> {
                        stackSearch.push(theNode);
                        theNode.setParentNode(node);
                    });
            if (node.equals(noWeightingGrid.getEndNode())) {
                reconstructPath(node); return false;
            }
        }
        return !stackSearch.isEmpty();
    }
}
