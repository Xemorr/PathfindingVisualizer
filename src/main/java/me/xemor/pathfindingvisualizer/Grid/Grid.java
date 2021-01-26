package me.xemor.pathfindingvisualizer.Grid;

import me.xemor.pathfindingvisualizer.Algorithms.Node;

public class Grid {

    private int columns;
    private int rows;
    protected Tile[][] grid;
    protected Node start;
    protected Node end;

    public Grid(int columns, int rows) {
        this.columns = columns;
        this.rows = rows;
        grid = new Tile[columns][rows];
        start = new Node(columns / 2, 3);
        grid[start.getX()][start.getY()] = Tile.START;
        end = new Node(columns / 2, rows - 4);
        grid[end.getX()][end.getY()] = Tile.END;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void setTile(int x, int z, Tile type) {
        grid[x][z] = type;
    }

    public Tile getTile(int x, int z) {
        return grid[x][z] == null ? Tile.EMPTY : grid[x][z];
    }

    public Tile getTile(Node node) { return grid[node.getX()][node.getY()] == null ? Tile.EMPTY : grid[node.getX()][node.getY()];}

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public void removeRoutes() {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Tile tile = getTile(i, j);
                if (tile == Tile.ROUTE) {
                    setTile(i, j, Tile.EMPTY);
                }
            }
        }
    }

    public void removeTracking() {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                Tile tile = getTile(i, j);
                if (tile == Tile.CHECKED) {
                    setTile(i, j, Tile.EMPTY);
                }
            }
        }
        setTile(start.getX(), start.getY(), Tile.START);
        setTile(end.getX(), end.getY(), Tile.END);
    }
}
