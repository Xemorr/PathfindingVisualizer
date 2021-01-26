package me.xemor.pathfindingvisualizer.Algorithms.AStar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.xemor.pathfindingvisualizer.Algorithms.Node;
import me.xemor.pathfindingvisualizer.Grid.Tile;

public class AStarNode implements Comparable<AStarNode> {

    private double fCost = Double.MAX_VALUE;
    private double gCost = Double.MAX_VALUE;
    private double hCost = Double.MAX_VALUE;
    private long insertionTime = Long.MIN_VALUE;
    protected Node node;
    protected AStarNode parentNode;

    public AStarNode(Node node) {
        this.node = node;
    }

    public void calculateFCost() {
        fCost = gCost + hCost;
    }

    public boolean partialGCost(AStarGrid aStarGrid, AStarNode aStarNode) {
        if (aStarNode != null) {
            if (aStarNode.getGCost() != Double.MAX_VALUE) {
                double newGCost = distance(aStarNode) + aStarNode.getGCost();
                if (newGCost < gCost) {
                    gCost = newGCost;
                    calculateFCost();
                    parentNode = aStarNode;
                    return true;
                }
            }
        }
        return false;
    }

    private double distance(AStarNode node) {
        int changeX = Math.abs(this.getNode().getX() - node.getNode().getX());
        int changeY = Math.abs(this.getNode().getY() - node.getNode().getY());
        if (changeX == changeY && changeX != 0) {
            return 1414;
        }
        return 1000;
    }

    public List<AStarNode> getNeighbours(AStarGrid aStarGrid) {
        return this.node.getNeighbours(aStarGrid.getGrid()).stream().map(AStarNode::new).collect(Collectors.toList());
    }

    public void calculateHCost(AStarGrid aStarGrid) { //Octile Distance Equation
        int changeX = aStarGrid.getGrid().getEnd().getX() - node.getX();
        int changeY = aStarGrid.getGrid().getEnd().getY() - node.getY();
        double d_max = Math.max(Math.abs(changeX), Math.abs(changeY));
        double d_min = Math.min(Math.abs(changeX), Math.abs(changeY));
        hCost = 1414 * d_min + ((d_max - d_min) * 1000);
    }

    public double getFCost() {
        return fCost;
    }

    public void setHCost(double hCost) {
        this.hCost = hCost;
    }

    public double getGCost() {
        return gCost;
    }

    public void setFCost(double fCost) {
        this.fCost = fCost;
    }

    public void setGCost(double gCost) {
        this.gCost = gCost;
    }

    public double getHCost() {
        return hCost;
    }

    public Node getNode() {
        return node;
    }

    public AStarNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(AStarNode parentNode) {
        this.parentNode = parentNode;
    }

    public long getInsertionTime() { return insertionTime; }

    public void setInsertionTime() { insertionTime = System.currentTimeMillis(); }

    @Override
    public int compareTo(AStarNode o) {
        int fCostComparison = Double.compare(this.getFCost(), o.getFCost());
        return fCostComparison == 0 ? Long.compare(o.insertionTime, this.insertionTime) : fCostComparison;
    }
}
