package me.xemor.pathfindingvisualizer.Algorithms;

import java.util.ArrayList;
import java.util.List;

import me.xemor.pathfindingvisualizer.Algorithms.AStar.AStarGrid;
import me.xemor.pathfindingvisualizer.Algorithms.AStar.AStarNode;
import me.xemor.pathfindingvisualizer.Grid.Grid;
import me.xemor.pathfindingvisualizer.Grid.Tile;

public class Node {

    private int x;
    private int y;
    Tile tile;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public List<Node> getNeighbours(Grid grid) {
        List<Node> nodes = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                Node node = new Node(this.getX() + i, this.getY() + j);
                if (node.getX() < 0 || node.getY() < 0) {
                    continue;
                }
                else if (node.getX() >= grid.getColumns() || node.getY() >= grid.getRows()) {
                    continue;
                }
                nodes.add(node);
            }
        }
        return nodes;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
