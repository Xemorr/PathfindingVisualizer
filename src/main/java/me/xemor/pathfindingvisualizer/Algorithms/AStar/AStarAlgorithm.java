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

    ArrayList<AStarNode> openNodes = new ArrayList<>();
    HashSet<AStarNode> closedNodes = new HashSet<>();
    boolean found;
    public AStarGrid aStarGrid;
    Node startNode;
    Node endNode;

    public AStarAlgorithm(GridView gridView, Speed speed) {
        super(gridView, speed);
        aStarGrid = new AStarGrid(grid);
        startNode = grid.getStart();
        endNode = grid.getEnd();
        AStarNode currentNode = new AStarNode(startNode);
        currentNode.calculateHCost(aStarGrid);
        currentNode.setGCost(0);
        currentNode.calculateFCost();
        openNodes.add(currentNode);
        found = false;
    }

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
        grid.removeTracking();
        gridView.postInvalidate();
    }



    @Override
    public boolean nextStep() {
        if (!openNodes.isEmpty()) {
            AStarNode currentNode = openNodes.get(0);
            grid.setTile(currentNode.getNode().getX(), currentNode.getNode().getY(), Tile.CHECKED);
            gridView.postInvalidate();
            closedNodes.add(currentNode);
            if (currentNode.getNode().equals(endNode)) {
                found = true;
            }
            openNodes.remove(currentNode);
            for (AStarNode neighbour : currentNode.getNeighbours(aStarGrid)) {
                if (grid.getTile(neighbour.getNode()) == Tile.OBSTACLE) {
                    continue;
                }
                neighbour.calculateHCost(aStarGrid);
                if (neighbour.partialGCost(aStarGrid, currentNode)) {
                    openNodes.add(neighbour);
                    neighbour.setInsertionTime();
                    Collections.sort(openNodes);
                }
            }
            if (found) {
                reconstructPath(currentNode); return false;
            }
        }
        if (openNodes.isEmpty()) {
            grid.removeTracking();
        }
        return !openNodes.isEmpty();
    }
}
