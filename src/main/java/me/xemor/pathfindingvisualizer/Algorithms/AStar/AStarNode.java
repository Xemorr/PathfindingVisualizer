package me.xemor.pathfindingvisualizer.Algorithms.AStar;

import java.util.List;
import java.util.stream.Collectors;

import me.xemor.pathfindingvisualizer.Algorithms.Node;

public class AStarNode implements Comparable<AStarNode> {

    /**
     * fCost is used to determine the order at which nodes are visited.
     */
    private double fCost = Double.MAX_VALUE;
    /**
     * gCost is used as part of the calculation for fCost and represents the distance from the start node.
     */
    private double gCost = Double.MAX_VALUE;
    /**
     * hCost is the heuristic and represents an estimate of the distance from the end node.
     */
    private double hCost = Double.MAX_VALUE;
    /**
     * Used as a decider between equal fCost values when sorting, improves the efficiency of the algorithm marginally. Measured in milliseconds.
     */
    private long insertionTime = Long.MIN_VALUE;
    /**
     * The Node this class is decorating
     */
    protected Node node;
    /**
     * The Node visited prior to this node being visited.
     */
    protected AStarNode parentNode;

    public AStarNode(Node node) {
        this.node = node;
    }

    /**
     * Calculates fCost from the internal gCost and hCost values (does not recalculate these)
     */
    public void calculateFCost() {
        fCost = gCost + hCost;
    }

    /**
     * This finds the best estimate of the gCost possible with the current nodes visited.
     * @param aStarGrid
     * @param aStarNode
     * @return Returns a boolean representing whether the gCost is lower than it was before, or the same.
     */
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

    /**
     * Returns the distance between adjacent nodes, used for calculating gCost
     * @param node
     * @return returns a double representing the distance
     */
    private double distance(AStarNode node) {
        int changeX = Math.abs(this.getNode().getX() - node.getNode().getX());
        int changeY = Math.abs(this.getNode().getY() - node.getNode().getY());
        if (changeX == changeY && changeX != 0) {
            return 1414;
        }
        return 1000;
    }

    /**
     * Returns the neighbours (including diagonal) of this AStarNode. Internally calls Node#getDiagNeighbours.
     * @param aStarGrid
     * @return
     */
    public List<AStarNode> getNeighbours(AStarGrid aStarGrid) {
        return this.node.getDiagNeighbours(aStarGrid.getGrid()).stream().map(node -> aStarGrid.getAStarNode(node)).collect(Collectors.toList());
    }

    /**
     * This uses the octile distance formulae to find an accurate birds-eye view distance from the end node to be used as an heuristic (hCost)
     * @param aStarGrid
     */
    public void calculateHCost(AStarGrid aStarGrid, double heuristicMultiplier) { //Octile Distance Equation
        int changeX = aStarGrid.getGrid().getEnd().getX() - node.getX();
        int changeY = aStarGrid.getGrid().getEnd().getY() - node.getY();
        double d_max = Math.max(Math.abs(changeX), Math.abs(changeY));
        double d_min = Math.min(Math.abs(changeX), Math.abs(changeY));
        hCost = (1414 * d_min + ((d_max - d_min) * 1000)) * heuristicMultiplier;
    }

    /**
     * Returns the fCost.
     * @return fCost
     */
    public double getFCost() {
        return fCost;
    }

    /**
     * Returns the gCost
     * @return gCost
     */
    public double getGCost() {
        return gCost;
    }

    /**
     * Sets the value of the heuristic.
     * @param hCost
     */
    public void setHCost(double hCost) {
        this.hCost = hCost;
    }

    /**
     * Sets the fCost.
     * @param fCost
     */
    public void setFCost(double fCost) {
        this.fCost = fCost;
    }

    /**
     * Sets the gCost.
     * @param gCost
     */
    public void setGCost(double gCost) {
        this.gCost = gCost;
    }

    /**
     * Sets the hCost
     * @return hCost
     */
    public double getHCost() {
        return hCost;
    }

    /**
     * Gets the Node
     * @return node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Gets the parent node
     * @return parentNode
     */
    public AStarNode getParentNode() {
        return parentNode;
    }

    /**
     * Sets the parentNode
     * @param parentNode
     */
    public void setParentNode(AStarNode parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * Gets the insertion time
     * @return insertionTime
     */
    public long getInsertionTime() { return insertionTime; }

    /**
     * Sets the insertion time to the current time
     * @see insertionTime
     */
    public void setInsertionTime() { insertionTime = System.currentTimeMillis(); }

    /**
     * Used within Collections#sort to decide sorting order. Sorts based on fCost, and if that fails, it sorts based on insertion time.
     * @param o
     * @return An integer representing greater than, equal or less than.
     */
    @Override
    public int compareTo(AStarNode o) {
        int fCostComparison = Double.compare(this.getFCost(), o.getFCost());
        return fCostComparison == 0 ? Long.compare(o.insertionTime, this.insertionTime) : fCostComparison;
    }
}
