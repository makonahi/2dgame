package com.example.demo;

import com.example.demo.gameobject.Location;
import com.example.demo.gameobject.Player;
import com.example.demo.gameobject.Tile;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Arrays;

public class GameRenderer {

    private final int TILE_SIZE = 100;

    private TextureManager textureManager;
    private Camera camera = new Camera();

    public GameRenderer(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public void renderLocation(Location location, Player player, Canvas canvas) {
        renderLocation(location, player, canvas, false);
    }

    public void renderLocation(Location location, Player player, Canvas canvas, boolean centerOnPlayer) {

        if (centerOnPlayer)
            camera.update(player);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        int[] locationSize = location.getSize();
        Tile[][] tiles = location.getTileArray();
        int renderTileSize = TILE_SIZE - camera.getConvertedSize();

        float[] cameraFOV = new float[]{
                camera.getY() / renderTileSize,
                camera.getSize() / renderTileSize + camera.getY() / renderTileSize,
                camera.getX() / renderTileSize,
                camera.getSize() / renderTileSize + camera.getX() / renderTileSize
        };

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        for (int y = (int) cameraFOV[0]; y < cameraFOV[1]; y++) {
            for (int x = (int) cameraFOV[2]; x < cameraFOV[3]; x++) {

                float worldX = x - camera.getX() / renderTileSize;
                float worldY = y - camera.getY()/ renderTileSize;

                if (x >= 0 && x < locationSize[0] && y >= 0 && y < locationSize[1]) {
                    Tile tile = tiles[x][y];
                    if (tile != null) {
                        Image texture = textureManager.getTexture(tile.getFileTextureName());
                        gc.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(),
                                worldX * renderTileSize, worldY * renderTileSize, renderTileSize, renderTileSize);
                    }
                }
            }
        }


        Image playerTexture = textureManager.getTexture("player.png");
        float playerRenderX = player.getX() - camera.getX() / renderTileSize;
        float playerRenderY = player.getY() - camera.getY()/ renderTileSize;
        gc.drawImage(playerTexture, 0, 0, playerTexture.getWidth(), playerTexture.getHeight(),
                playerRenderX * renderTileSize, playerRenderY * renderTileSize, renderTileSize, renderTileSize);
    }

    public void setCameraSize(int size){
        this.camera.setSize(size);
    }

    public void setCameraXY(float x, float y){
        this.camera.update(x, y);
    }

    public float getCameraX() {
        return this.camera.getX();
    }

    public float getCameraY() {
        return this.camera.getY();
    }
}
