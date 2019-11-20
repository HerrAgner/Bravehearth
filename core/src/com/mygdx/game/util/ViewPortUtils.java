package com.mygdx.game.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ViewPortUtils {

    private static final int DEFAULT_CELL_SIZE = 1;

    private ViewPortUtils() { }

    public static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer) {
        drawGrid(viewport, shapeRenderer, DEFAULT_CELL_SIZE);
    }

    private static void drawGrid(Viewport viewport, ShapeRenderer shapeRenderer, int cellSize) {
        if (viewport == null) {
            throw new IllegalArgumentException("viewport param is required.");
        }

        if (shapeRenderer == null) {
            throw new IllegalArgumentException("ShapeRenderer param is required");
        }

        if (cellSize < DEFAULT_CELL_SIZE) {
            cellSize = DEFAULT_CELL_SIZE;
        }

        // Copy old color from render
        Color oldColor = new Color(shapeRenderer.getColor());

        int worldWidth = (int) viewport.getWorldWidth();
        int worldHeight = (int) viewport.getWorldHeight();
        int doubleWorldWidth = worldWidth * 2;
        int doubleWorldHeight = worldHeight * 2;

        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);

        // Draw vertical lines
        for (int i = -doubleWorldWidth; i < doubleWorldWidth; i += cellSize) {
            shapeRenderer.line(i, -doubleWorldHeight, i, doubleWorldHeight);
        }

        // Draw horizontal lines
        for (int y = -doubleWorldHeight; y < doubleWorldHeight; y += cellSize) {
            shapeRenderer.line(-doubleWorldWidth, y, doubleWorldWidth, y);
        }

        // Draw x-y axis lines
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(0, -doubleWorldHeight, 0, doubleWorldHeight);
        shapeRenderer.line(-doubleWorldWidth, 0, doubleWorldWidth, 0);


        // draw world bounds
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(0, worldHeight, worldWidth, worldHeight);
        shapeRenderer.line(worldWidth, 0, worldWidth, worldHeight);

        shapeRenderer.end();

        shapeRenderer.setColor(oldColor);
    }
}

