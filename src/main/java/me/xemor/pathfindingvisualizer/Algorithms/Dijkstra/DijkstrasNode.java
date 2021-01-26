package me.xemor.pathfindingvisualizer.Algorithms.Dijkstra;

import me.xemor.pathfindingvisualizer.Algorithms.AStar.AStarNode;
import me.xemor.pathfindingvisualizer.Algorithms.Node;

public class DijkstrasNode implements Comparable<DijkstrasNode> {

    private double distance = Double.MAX_VALUE;
    protected Node node;
    protected DijkstrasNode parentNode;

    public void calculateDistance(AStarNode node) {
        int changeX = Math.abs(this.getNode().getX() - node.getNode().getX());
        int changeY = Math.abs(this.getNode().getY() - node.getNode().getY());
        if (changeX == changeY && changeX != 0) {
            this.distance = 1414;
        }
        this.distance =  1000;
    }

    public double getDistance() {
        return distance;
    }

    public Node getNode() {
        return node;
    }

    public DijkstrasNode getParentNode() {
        return parentNode;
    }

    @Override
    public int compareTo(DijkstrasNode o) {
        return Double.compare(this.distance, o.distance);
    }
}
