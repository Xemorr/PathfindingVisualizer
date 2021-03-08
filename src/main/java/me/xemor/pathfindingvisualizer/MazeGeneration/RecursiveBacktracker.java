package me.xemor.pathfindingvisualizer.MazeGeneration;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import androidx.viewpager.widget.PagerAdapter;
import me.xemor.pathfindingvisualizer.Algorithms.Node;
import me.xemor.pathfindingvisualizer.Grid.Grid;
import me.xemor.pathfindingvisualizer.Grid.GridView;
import me.xemor.pathfindingvisualizer.Grid.Tile;


/**
 * Creates a traditional maze with a guaranteed path.
 */
public class RecursiveBacktracker implements MazeGenerator {

    GridView gridView;
    Deque<Node> stack = new ArrayDeque<>();
    HashSet<Node> closedNodes = new HashSet<>();


    public RecursiveBacktracker(GridView gridView) {
        this.gridView = gridView;
        stack.push(gridView.getGrid().getStart());
        closedNodes.add(gridView.getGrid().getStart());
        closedNodes.add(gridView.getGrid().getEnd());
    }

    /**
     * Overrides the generate method within the MazeGenerator interface.
     * This generate method initially sets all tiles in the grid (aside from the start and end tiles)
     * to obstacles in preparation for the recursive backtracker implementation to be executed.
     */
    @Override
    public void generate() {
        Grid grid = gridView.getGrid();
        for (int i = 0; i < grid.getColumns(); i++) {
            for (int j = 0; j < grid.getRows(); j++) {
                Tile tile = grid.getTile(i, j);
                if (tile == Tile.START || tile == Tile.END) {
                    continue;
                }
                grid.setTile(i, j, Tile.OBSTACLE);
            }
        }
        do {
            nextStep();
        } while(!stack.isEmpty());
        gridView.postInvalidate();
    }

    /**
     * The method that handles each iterative step of the recursive backtracking algorithm,
     * I chose to do it iteratively in order to avoid StackOverflow errors on my larger grid
     * type.
     */
    public void nextStep() {
        Grid grid = gridView.getGrid();
        Node currentNode = stack.pop();
        List<Node> neighbours = getNeighbours(currentNode, grid);
        neighbours.removeIf(node -> closedNodes.contains(node));
        if (neighbours.size() > 0) {
            stack.push(currentNode);
            Node node = neighbours.get(ThreadLocalRandom.current().nextInt(neighbours.size()));
            int xDifference = currentNode.getX() - node.getX();
            int yDifference = currentNode.getY() - node.getY();
            Node corridorNode = new Node(node.getX() + (xDifference / 2), node.getY() + (yDifference / 2));
            Tile tile = grid.getTile(corridorNode);
            if (tile != Tile.START && tile != Tile.END) {
                grid.setTile(corridorNode, Tile.EMPTY);
            }
            grid.setTile(node.getX(), node.getY(), Tile.EMPTY);
            closedNodes.add(node);
            stack.push(node);
        }
    }

    /**
     * Recursive Backtracker thinks of neighbours as things two places away as typically you can place walls on any side of the tile
     * without a wall counting as a tile in and of itself. This meant I had to make a special getNeighbours method for this very
     * purpose
     * @param currentNode
     * @param grid
     * @return
     */
    public List<Node> getNeighbours(Node currentNode, Grid grid) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(currentNode.getX(), currentNode.getY() - 2));
        nodes.add(new Node(currentNode.getX(), currentNode.getY() + 2));
        nodes.add(new Node(currentNode.getX() + 2, currentNode.getY()));
        nodes.add(new Node(currentNode.getX() - 2, currentNode.getY()));

        nodes = nodes.stream().filter(node -> node.getX() >= 0 && node.getX() < grid.getColumns() && node.getY() >= 0 && node.getY() < grid.getRows()).collect(Collectors.toList());
        return nodes;
    }

}
