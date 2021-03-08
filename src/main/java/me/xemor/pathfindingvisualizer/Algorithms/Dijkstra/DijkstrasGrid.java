package me.xemor.pathfindingvisualizer.Algorithms.Dijkstra;

import me.xemor.pathfindingvisualizer.Algorithms.AStar.AStarNode;
import me.xemor.pathfindingvisualizer.Algorithms.Node;
import me.xemor.pathfindingvisualizer.Grid.Grid;

public class DijkstrasGrid {

    private DijkstrasNode[][] dijkstrasNodes;
    private Grid grid;
    DijkstrasNode startNode;
    DijkstrasNode endNode;

    public DijkstrasGrid(Grid grid) {
        this.grid = grid;
        dijkstrasNodes = new DijkstrasNode[grid.getColumns()][grid.getRows()];
        startNode = new DijkstrasNode(grid.getStart());
        endNode = new DijkstrasNode(grid.getEnd());
        dijkstrasNodes[startNode.getNode().getX()][startNode.getNode().getY()] = startNode;
        dijkstrasNodes[endNode.getNode().getX()][endNode.getNode().getY()] = endNode;
        startNode.setDistance(0);
    }

    /**
     * Returns the underlying grid
     * @return
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     *This method returns the Dijkstras Node corresponding with the two coordinates passed in.
     * @param x
     * @param y
     * @return
     */
    public DijkstrasNode getDijkstrasNode(int x, int y) {
        DijkstrasNode dijkstrasNode = dijkstrasNodes[x][y];
        if (dijkstrasNode == null) {
            Node node = new Node(x, y);
            dijkstrasNode = new DijkstrasNode(node);
            dijkstrasNodes[x][y] = dijkstrasNode;
        }
        return dijkstrasNode;
    }

    /**
     * A convenience method that internally calls getDijkstrasNode(int x, int y)
     * @param node
     * @return
     */
    public DijkstrasNode getDijkstrasNode(Node node) {
        return getDijkstrasNode(node.getX(), node.getY());
    }

    /**
     * Sets the dijkstras node in the grid to the argument.
     * @param dijkstrasNode
     */
    public void setDijkstrasNode(DijkstrasNode dijkstrasNode) {
        dijkstrasNodes[dijkstrasNode.getNode().getX()][dijkstrasNode.getNode().getY()] = dijkstrasNode;
    }

    /**
     * Returns the start node.
     * @return startNode
     */
    public DijkstrasNode getStartNode() {
        return startNode;
    }

    /**
     * Returns the end node.
     * @return endNode
     */
    public DijkstrasNode getEndNode() {
        return endNode;
    }

}
