package me.xemor.pathfindingvisualizer.Grid;

import android.graphics.Color;
import android.graphics.Paint;

public enum Tile {

    /**
     * A list of all of the possible tiles within the application.
     */
    START(Color.GREEN), OBSTACLE(Color.BLACK),END(Color.RED),CHECKED(Color.BLUE),ROUTE(Color.YELLOW), EMPTY(Color.WHITE, true);

    /**
     * An integer representing the colour of hte tile.
     */
    private final int color;
    /**
     * A Paint.Style object representing how to draw the colour of the tile.
     */
    private final Paint.Style style;
    /**
     * A boolean representing whether to give the tile a black border or not.
     */
    private final boolean hasBorder;

    Tile(int color) {
        this.color = color;
        this.style = Paint.Style.FILL;
        this.hasBorder = false;
    }

    Tile(int color, Paint.Style style) {
        this.color = color;
        this.style = style;
        this.hasBorder = false;
    }

    Tile(int color, boolean hasBorder) {
        this.color = color;
        this.style = Paint.Style.FILL;
        this.hasBorder = hasBorder;
    }

    public int getColor() {
        return color;
    }

    public Paint.Style getStyle() {
        return style;
    }

    public boolean hasBorder() {
        return hasBorder;
    }

}
