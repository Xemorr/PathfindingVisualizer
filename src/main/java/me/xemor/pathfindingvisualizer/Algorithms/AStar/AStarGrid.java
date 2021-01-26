package me.xemor.pathfindingvisualizer.Algorithms.AStar;

import me.xemor.pathfindingvisualizer.Algorithms.Node;
import me.xemor.pathfindingvisualizer.Grid.Grid;

public class AStarGrid {

    private AStarNode[][] aStarNodes;
    private Grid grid;

    public AStarGrid(Grid grid) {
        this.grid = grid;
        aStarNodes = new AStarNode[grid.getColumns()][grid.getRows()];
        AStarNode startNode = createAStarNode(grid.getStart());
        AStarNode endNode = createAStarNode(grid.getEnd());
        aStarNodes[startNode.getNode().getX()][startNode.getNode().getY()] = startNode;
        aStarNodes[endNode.getNode().getX()][endNode.getNode().getY()] = endNode;
        startNode.setGCost(0);
        endNode.setHCost(0);
    }

    private AStarNode createAStarNode(Node node) {
        return new AStarNode(node);
    }

    public Grid getGrid() {
        return grid;
    }

    public AStarNode getAStarNode(int x, int y) {
        AStarNode aStarNode = aStarNodes[x][y];
        if (aStarNode == null) {
            Node node = new Node(x, y);
            aStarNode = new AStarNode(node);
            aStarNodes[x][y] = aStarNode;
        }
        return aStarNode;
    }

    public AStarNode getAStarNode(Node node) {
        return getAStarNode(node.getX(), node.getY());
    }

    public void setAStarNode(AStarNode aStarNode) {
        aStarNodes[aStarNode.getNode().getX()][aStarNode.getNode().getY()] = aStarNode;
    }

}
