package me.xemor.pathfindingvisualizer.Algorithms.AStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

import me.xemor.pathfindingvisualizer.Algorithms.Algorithm;
import me.xemor.pathfindingvisualizer.Algorithms.Node;
import me.xemor.pathfindingvisualizer.Algorithms.Speed;
import me.xemor.pathfindingvisualizer.Grid.GridView;
import me.xemor.pathfindingvisualizer.Grid.Tile;

public class AStarAlgorithm extends Algorithm {

    protected PriorityQueue<AStarNode> openNodes = new PriorityQueue<>();
    protected HashSet<AStarNode> closedNodes = new HashSet<>();
    protected AStarGrid aStarGrid;
    protected Node startNode;
    protected Node endNode;
    protected int heuristicWeighting;

    public AStarAlgorithm(GridView gridView, Speed speed, int heuristicWeighting) {
        super(gridView, speed);
        this.heuristicWeighting = heuristicWeighting;
        aStarGrid = new AStarGrid(grid);
        startNode = grid.getStart();
        endNode = grid.getEnd();
        AStarNode currentNode = new AStarNode(startNode);
        currentNode.calculateHCost(aStarGrid, heuristicWeighting);
        currentNode.setGCost(0);
        currentNode.calculateFCost();
        openNodes.add(currentNode);
    }

    /**
     * This method draws the optimal path onto the grid, once it has been formed.
     * @param currentNode
     */
    public void reconstructPath(AStarNode currentNode) {
        while (true) {
            currentNode = currentNode.getParentNode();
            if (currentNode.getNode().equals(grid.getStart())) {
                break;
            }
            else {
                grid.setTile(currentNode.getNode().getX(), currentNode.getNode().getY(), Tile.ROUTE);
            }
        }
    }

    /**
     * This method defines each individual step of this iterative A* Implementation
     * @return
     */
    @Override
    public boolean nextStep() {
        boolean found = false;
        if (!openNodes.isEmpty()) {
            AStarNode currentNode = openNodes.poll();
            grid.setTile(currentNode.getNode().getX(), currentNode.getNode().getY(), Tile.CHECKED);
            gridView.postInvalidate();
            closedNodes.add(currentNode);
            if (currentNode.getNode().equals(grid.getEnd())) {
                found = true;
            }
            openNodes.remove(currentNode);
            for (AStarNode neighbour : currentNode.getNeighbours(aStarGrid)) {
                if (grid.getTile(neighbour.getNode()) == Tile.OBSTACLE) {
                    continue;
                }
                if (closedNodes.contains(neighbour)) {
                    continue;
                }
                neighbour.calculateHCost(aStarGrid, heuristicWeighting);
                openNodes.remove(neighbour);
                if (neighbour.partialGCost(aStarGrid, currentNode)) {
                    neighbour.setInsertionTime();
                }
                openNodes.add(neighbour);
            }
            if (found) {
                reconstructPath(currentNode); return false;
            }
        }
        return !openNodes.isEmpty();
    }
}
