package me.xemor.pathfindingvisualizer.Algorithms.NoHeuristic;

import me.xemor.pathfindingvisualizer.Algorithms.Node;
import me.xemor.pathfindingvisualizer.Grid.Grid;

public class NoWeightingGrid {

    private NoWeightingNode[][] noWeightingNodes;
    private Grid grid;
    NoWeightingNode startNode;
    NoWeightingNode endNode;

    public NoWeightingGrid(Grid grid) {
        this.grid = grid;
        noWeightingNodes = new NoWeightingNode[grid.getColumns()][grid.getRows()];
        startNode = new NoWeightingNode(grid.getStart());
        endNode = new NoWeightingNode(grid.getEnd());
        noWeightingNodes[startNode.getNode().getX()][startNode.getNode().getY()] = startNode;
        noWeightingNodes[endNode.getNode().getX()][endNode.getNode().getY()] = endNode;
    }

    /**
     * Returns the underlying grid
     * @return grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * This method returns the DepthFirstNode corresponding with the two coordinates passed in.
     * @param x
     * @param y
     * @return DepthFirstNode
     */
    public NoWeightingNode getDepthFirstNode(int x, int y) {
        NoWeightingNode noWeightingNode = noWeightingNodes[x][y];
        if (noWeightingNode == null) {
            Node node = new Node(x, y);
            noWeightingNode = new NoWeightingNode(node);
            noWeightingNodes[x][y] = noWeightingNode;
        }
        return noWeightingNode;
    }

    /**
     * Convenience method that allows you to get a DepthFirstNode using a Node object rather than x and y coordinates.
     * @param node
     * @return DepthFirstNode
     */
    public NoWeightingNode getDepthFirstNode(Node node) {
        return getDepthFirstNode(node.getX(), node.getY());
    }

    /**
     * Sets DepthFirstNode to its correct position within the grid.
     * @param noWeightingNode
     */
    public void setDepthFirstNode(NoWeightingNode noWeightingNode) {
        noWeightingNodes[noWeightingNode.getNode().getX()][noWeightingNode.getNode().getY()] = noWeightingNode;
    }

    /**
     * Gets the DepthFirstNode representing the start position
     * @return DepthFirstNode
     */
    public NoWeightingNode getStartNode() {
        return startNode;
    }

    /**
     * Gets the DepthFirstNode representing the end position
     * @return DepthFirstNode
     */
    public NoWeightingNode getEndNode() {
        return endNode;
    }

}
