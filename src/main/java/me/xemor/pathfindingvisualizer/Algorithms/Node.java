package me.xemor.pathfindingvisualizer.Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import me.xemor.pathfindingvisualizer.Grid.Grid;

/**
 * A class representing a position on the grid.
 */
public class Node {

    private final int x;
    private final int y;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets all neighbours, including diagonal neighbours of the node from the grid.
     * @param grid
     * @return List<Node> representing the neighbours.
     */
    public List<Node> getDiagNeighbours(Grid grid) {
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

    /**
     * Gets all neighbours that the tile is adjacent to from the grid.
     * @param grid
     * @return
     */
    public List<Node> getNeighbours(Grid grid) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(x, y - 1));
        nodes.add(new Node(x, y + 1));
        nodes.add(new Node(x + 1, y));
        nodes.add(new Node(x - 1, y));

        nodes = nodes.stream().filter(node -> node.getX() >= 0 && node.getX() < grid.getColumns() && node.getY() >= 0 && node.getY() < grid.getRows()).collect(Collectors.toList());
        return nodes;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Used for comparing whether two nodes are equal, this has to be overridden because
     * otherwise by default, java simply compares whether the two memory locations are equal,
     * this means that if you have two node objects representing the same position,
     * the equals method would fail by default.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x &&
                y == node.y;
    }

    /**
     * Used within my hashmaps, creates a hash based on the two coordinates within the grid.
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
