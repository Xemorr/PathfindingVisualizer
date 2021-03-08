package me.xemor.pathfindingvisualizer.Algorithms.Dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

import me.xemor.pathfindingvisualizer.Algorithms.Algorithm;
import me.xemor.pathfindingvisualizer.Algorithms.Node;
import me.xemor.pathfindingvisualizer.Algorithms.Speed;
import me.xemor.pathfindingvisualizer.Grid.GridView;
import me.xemor.pathfindingvisualizer.Grid.Tile;

public class DijkstrasAlgorithm extends Algorithm {

    PriorityQueue<DijkstrasNode> openNodes = new PriorityQueue<>();
    HashSet<DijkstrasNode> closedNodes = new HashSet<>();
    public DijkstrasGrid dijkstrasGrid;

    public DijkstrasAlgorithm(GridView gridView, Speed speed) {
        super(gridView, speed);
        dijkstrasGrid = new DijkstrasGrid(grid);
        openNodes.add(dijkstrasGrid.getStartNode());
    }

    /**
     * This method draws the optimal path onto the grid, once it has been formed.
     * @param currentNode
     */
    public void reconstructPath(DijkstrasNode currentNode) {
        while (true) {
            currentNode = currentNode.getParentNode();
            if (currentNode.equals(dijkstrasGrid.getStartNode())) {
                break;
            }
            else {
                grid.setTile(currentNode.getNode().getX(), currentNode.getNode().getY(), Tile.ROUTE);
            }
        }
    }


    /**
     * This method defines each individual step of this iterative Dijkstras Implementation
     * @return
     */
    @Override
    public boolean nextStep() {
        boolean found = false;
        if (!openNodes.isEmpty()) {
            DijkstrasNode currentNode = openNodes.poll();
            grid.setTile(currentNode.getNode().getX(), currentNode.getNode().getY(), Tile.CHECKED);
            gridView.postInvalidate();
            closedNodes.add(currentNode);
            if (currentNode.equals(dijkstrasGrid.getEndNode())) {
                found = true;
            }
            openNodes.remove(currentNode);
            for (DijkstrasNode neighbour : currentNode.getNeighbours(dijkstrasGrid)) {
                if (grid.getTile(neighbour.getNode()) == Tile.OBSTACLE) {
                    continue;
                }
                if (!closedNodes.contains(neighbour)) {
                    openNodes.remove(neighbour); //removed and added to ensure it is in sorted order.
                    boolean isShorter = neighbour.calculateNewDistance(currentNode);
                    if (isShorter) {
                        neighbour.setParentNode(currentNode);
                    }
                    openNodes.add(neighbour);
                }
            }
            if (found) {
                reconstructPath(currentNode); return false;
            }
        }
        return !openNodes.isEmpty();
    }

}
