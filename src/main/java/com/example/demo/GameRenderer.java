package com.example.demo;

import com.example.demo.gameobject.Entity;
import com.example.demo.gameobject.Location;
import com.example.demo.gameobject.Player;
import com.example.demo.gameobject.Tile;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;

public class GameRenderer {

    private final int TILE_SIZE = 100;

    private TextureManager textureManager;

    public GameRenderer(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public void renderLocation(Canvas canvas, Location location){

        Tile[][] tiles = location.getTileArray();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int y = 0; y < location.getHeight(); y++) {
            for (int x = 0; x < location.getWidth(); x++) {
                    Tile tile = tiles[x][y];
                    if (tile != null) {
                        Image texture = textureManager.getTexture(tile.getFileTextureName());
                        gc.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(),
                                x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }

            }
        }
    }

    public void renderEntites(Canvas canvas, Location location, Player player){

        ArrayList<Entity> entities = location.getEntitiesArray();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());

        for (Entity entity: entities){
            Image texture = textureManager.getTexture(entity.getFileTextureName());
            gc.drawImage(texture, 0, 0, texture.getWidth(), texture.getHeight(),
                    entity.getX() * TILE_SIZE, entity.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }

        Image playerTexture = textureManager.getTexture("player.png");
        gc.drawImage(playerTexture, 0, 0, playerTexture.getWidth(), playerTexture.getHeight(),
                player.getX() * TILE_SIZE, player.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}
