package me.xemor.pathfindingvisualizer.Algorithms.AStar;

import me.xemor.pathfindingvisualizer.Algorithms.Node;
import me.xemor.pathfindingvisualizer.Grid.Grid;

public class AStarGrid {

    private AStarNode[][] aStarNodes;
    private Grid grid;

    public AStarGrid(Grid grid) {
        this.grid = grid;
        aStarNodes = new AStarNode[grid.getColumns()][grid.getRows()];
        AStarNode startNode = new AStarNode(grid.getStart());
        AStarNode endNode = new AStarNode(grid.getEnd());
        aStarNodes[startNode.getNode().getX()][startNode.getNode().getY()] = startNode;
        aStarNodes[endNode.getNode().getX()][endNode.getNode().getY()] = endNode;
        startNode.setGCost(0);
        endNode.setHCost(0);
    }

    /**
     * Returns the underlying grid
     * @return Grid
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * This method returns the AStarNode corresponding with the two-coordinate passed in.
     * @param x The x-coordinate to take the AStarNode from
     * @param y The y-coordinate to take the AStarNode from
     * @return The AStarNode corresponding with that location
     */
    public AStarNode getAStarNode(int x, int y) {
        AStarNode aStarNode = aStarNodes[x][y];
        if (aStarNode == null) {
            Node node = new Node(x, y);
            aStarNode = new AStarNode(node);
            aStarNodes[x][y] = aStarNode;
        }
        return aStarNode;
    }

    /**
     * This is a convenience method that called getAStarNode(x, y) but just takes the x-coordinates and y-coordinates from the Node object.
     * @param node
     * @return AStarNode
     */
    public AStarNode getAStarNode(Node node) {
        return getAStarNode(node.getX(), node.getY());
    }

    public void setAStarNode(AStarNode aStarNode) {
        aStarNodes[aStarNode.getNode().getX()][aStarNode.getNode().getY()] = aStarNode;
    }

}
