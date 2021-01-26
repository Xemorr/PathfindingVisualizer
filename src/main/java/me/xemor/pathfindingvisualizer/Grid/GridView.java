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
import me.xemor.pathfindingvisualizer.Algorithms.AStar.AStarGrid;
import me.xemor.pathfindingvisualizer.Algorithms.AStar.AStarNode;
import me.xemor.pathfindingvisualizer.Algorithms.Algorithm;
import me.xemor.pathfindingvisualizer.Algorithms.Node;
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
    AStarGrid aStarGrid;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = widthMeasureSpec;
        height = heightMeasureSpec;
    }

    public void recreateGrid(int columns, int rows) {
        grid = new Grid(columns, rows); invalidate();
    }

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
        for (int i = 0; i < grid.getColumns(); i++) {
            for (int j = 0; j < grid.getRows(); j++) {
                Tile currentTile = grid.getTile(i, j);
                if (currentTile == Tile.CHECKED) {
                    Paint paint2 = new Paint();
                    paint2.setStyle(Paint.Style.FILL);
                    paint2.setColor(Color.WHITE);
                    paint2.setTextSize(30);
                    AStarNode aStarNode = aStarGrid.getAStarNode(i, j);
                    double fCost = aStarNode.getFCost();
                    float rectX = i * tileWidth;
                    float rectY = j * tileHeight;
                    float endRectY = rectY + tileHeight;
                    fCost = ((int) (fCost * 1000)) / 1000.0;
                    canvas.drawText(String.valueOf(fCost), rectX, endRectY, paint2);
                }
            }
        }
    }

    public void drawTile(Canvas canvas, Tile tile, int x, int y) {
        float rectX = x * tileWidth;
        float rectY = y * tileHeight;
        float endRectX = rectX + tileWidth;
        float endRectY = rectY + tileHeight;
        paint.setStyle(tile.style);
        paint.setColor(tile.color);
        if (tile.hasBorder) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.BLACK);
        }
        canvas.drawRect(rectX, rectY, endRectX, endRectY, paint);
    }

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

    public void destroyRoute() {
        grid.removeRoutes();
        invalidate();
    }

    public void runAlgorithm(Speed speed) {
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(this, speed);
        this.aStarGrid = aStarAlgorithm.aStarGrid;
        destroyRoute();
        aStarAlgorithm.beginAlgorithm();
    }

    public Grid getGrid() {
        return grid;
    }
}
