package me.xemor.pathfindingvisualizer.Algorithms.Dijkstra;

import java.util.List;
import java.util.stream.Collectors;

import me.xemor.pathfindingvisualizer.Algorithms.Node;

public class DijkstrasNode implements Comparable<DijkstrasNode> {

    private double distance = Double.MAX_VALUE;
    private Node node;
    private DijkstrasNode parentNode;

    public DijkstrasNode(Node node) {
        this.node = node;
    }

    /**
     * Calculates the new distance from the start node.
     * @param neighbour
     * @return
     */
    public boolean calculateNewDistance(DijkstrasNode neighbour) {
        double tempDistance;
        int changeX = Math.abs(this.getNode().getX() - neighbour.getNode().getX());
        int changeY = Math.abs(this.getNode().getY() - neighbour.getNode().getY());
        if (changeX == changeY && changeX != 0) {
            tempDistance = neighbour.getDistance() + 1414;
        }
        else {
            tempDistance = neighbour.getDistance() + 1000;
        }
        boolean isSmaller = tempDistance < distance;
        distance = Math.min(tempDistance, distance);
        return isSmaller;
    }

    /**
     * A simple setter method for the distance from start node
     * @param distance
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Returns the neighbours (including diagonal) of this DijkstrasNode. Internally calls Node#getDiagNeighbours.
     * @param dijkstrasGrid
     * @return List<DijkstrasNode>
     */
    public List<DijkstrasNode> getNeighbours(DijkstrasGrid dijkstrasGrid) {
        return this.getNode().getDiagNeighbours(dijkstrasGrid.getGrid()).stream().map(dijkstrasGrid::getDijkstrasNode).collect(Collectors.toList());
    }

    /**
     * Sets the parentNode
     * @param parentNode
     */
    public void setParentNode(DijkstrasNode parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * Gets the distance
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Gets the node
     * @return node
     */
    public Node getNode() {
        return node;
    }

    /**
     * Gets the parent node
     * @return parentNode
     */
    public DijkstrasNode getParentNode() {
        return parentNode;
    }

    /**
     * Used within Collections#sort to decide sorting order. Sorts based on distance.
     * @param o
     * @return An integer representing greater than, equal or less than.
     */
    @Override
    public int compareTo(DijkstrasNode o) {
        return Double.compare(this.distance, o.distance);
    }
}
