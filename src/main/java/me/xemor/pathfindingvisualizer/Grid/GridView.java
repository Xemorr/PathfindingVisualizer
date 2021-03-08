package me.xemor.pathfindingvisualizer.Grid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import me.xemor.pathfindingvisualizer.Algorithms.AStar.AStarAlgorithm;
import me.xemor.pathfindingvisualizer.Algorithms.Algorithm;
import me.xemor.pathfindingvisualizer.Algorithms.AlgorithmType;
import me.xemor.pathfindingvisualizer.Algorithms.Dijkstra.DijkstraAStarAlgorithm;
import me.xemor.pathfindingvisualizer.Algorithms.Dijkstra.DijkstrasAlgorithm;
import me.xemor.pathfindingvisualizer.Algorithms.NoHeuristic.BreadthFirstAlgorithm;
import me.xemor.pathfindingvisualizer.Algorithms.NoHeuristic.DepthFirstAlgorithm;
import me.xemor.pathfindingvisualizer.Algorithms.Speed;

public class GridView extends View {

    int width;
    int height;
    private Grid grid = new Grid(18, 32);
    Paint paint = new Paint();
    float tileWidth;
    float tileHeight;
    int lastColumn;
    int lastRow;

    public GridView(Context context) {
        super(context);
        init(context);
    }

    public GridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
    }

    /**
     * The method called when the device's size is found.
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = widthMeasureSpec;
        height = heightMeasureSpec;
    }

    /**
     * Recreates the grid using the columns and rows stored within the object.
     * @param columns
     * @param rows
     */
    public void recreateGrid(int columns, int rows) {
        grid = new Grid(columns, rows); invalidate();
    }

    /**
     * The method called every time invalidate(); is ran.
     * This method updates what is shown within the GridView to the current state of the grid.
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        tileWidth = this.getMeasuredWidth() / grid.getColumns();
        tileHeight = this.getMeasuredHeight() / grid.getRows();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        for (int i = 0; i < grid.getColumns(); i++) {
            for (int j = 0; j < grid.getRows(); j++) {
                Tile currentTile = grid.getTile(i, j);
                drawTile(canvas, currentTile, i, j);
            }
        }
    }

    /**
     * An internal method called by onDraw to draw the specified tile.
     * @param canvas
     * @param tile
     * @param x
     * @param y
     */
    private void drawTile(Canvas canvas, Tile tile, int x, int y) {
        float rectX = x * tileWidth;
        float rectY = y * tileHeight;
        float endRectX = rectX + tileWidth;
        float endRectY = rectY + tileHeight;
        paint.setStyle(tile.getStyle());
        paint.setColor(tile.getColor());
        if (tile.hasBorder()) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
        }
        canvas.drawRect(rectX, rectY, endRectX, endRectY, paint);
    }

    /**
     * The method that is called whenever the user interacts with the GridView through touch.
     * Its primary job is to flip the state of tiles between obstacles and empty tiles when they are touched.
     * It also has the secondary purpose of being used to clear the route after a pathfinding algorithm is executed.
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE  || motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int currentColumn = (int) Math.floor(x / tileWidth);
            int currentRow = (int) Math.floor(y / tileHeight);
            if (currentColumn == lastColumn && currentRow == lastRow && motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                return true;
            }
            if (currentColumn <= -1 || currentRow <= -1
                    || currentColumn >= grid.getColumns() || currentRow >= grid.getRows()) {
                return true;
            }
            Tile currentTile = grid.getTile(currentColumn, currentRow);
            switch (currentTile) {
                case EMPTY: grid.setTile(currentColumn, currentRow, Tile.OBSTACLE); invalidate(); break;
                case OBSTACLE: grid.setTile(currentColumn, currentRow, Tile.EMPTY); invalidate(); break;
                case ROUTE: destroyRoute(); break;
            }
            lastColumn = currentColumn;
            lastRow = currentRow;
        }
        return true;
    }

    /**
     * Removes the routes from the internal grid object and then redraws the screen.
     */
    public void destroyRoute() {
        grid.removeRoutes();
        invalidate();
    }

    /**
     * This method instantiates the correct Algorithm based on the AlgorithmType input and then executes the algorithm.
     * @param speed
     * @param algorithmType
     */
    public void runAlgorithm(Speed speed, AlgorithmType algorithmType, int heuristicWeighting) {
        Algorithm algorithm = null;
        switch (algorithmType) {
            case ASTAR: algorithm = new AStarAlgorithm(this, speed, heuristicWeighting); break;
            case DIJKSTRAS: algorithm = new DijkstraAStarAlgorithm(this, speed); break;
            case DEPTHFIRST: algorithm = new DepthFirstAlgorithm(this, speed);
        }
        destroyRoute();
        algorithm.beginAlgorithm();
    }

    public Grid getGrid() {
        return grid;
    }
}
