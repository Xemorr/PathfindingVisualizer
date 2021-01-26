package me.xemor.pathfindingvisualizer.Grid;

import android.graphics.Color;
import android.graphics.Paint;

public enum Tile {

    START(Color.GREEN), OBSTACLE(Color.BLACK),END(Color.RED),CHECKED(Color.BLUE),ROUTE(Color.YELLOW), EMPTY(Color.WHITE, true);

    int color;
    Paint.Style style;
    boolean hasBorder;

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

}
