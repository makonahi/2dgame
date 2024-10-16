package com.example.demo;

import com.example.demo.gameobject.Location;
import com.example.demo.gameobject.Player;
import com.example.demo.gameobject.Tile;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Game {

    private static final JsonManager jman = new JsonManager();

    private double mouseStartX, mouseStartY;
    private boolean isDragging = false;

    private static final int CANVAS_WIDTH = 900;
    private static final int CANVAS_HEIGHT = 900;

    private final GameRenderer renderer;
    @SuppressWarnings("all")
    private Player player = new Player(1, 1);
    @SuppressWarnings("all")
    private ResourceBar rBar;
    private Location location;
    private Canvas canvas;

    public Game() {
        TextureManager textureManager = new TextureManager();

        textureManager.preloadTextures(new String[] {
                "floor.png",
                "wall.png",
                "player.png"
        });

        renderer = new GameRenderer(textureManager);
    }

    public Scene getGameScene(Stage stage, String locationFileName) {

        if (Objects.isNull(location))
            location = jman.readJsonObject(locationFileName, Location.class);

        StackPane root = new StackPane();
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        rBar = new ResourceBar(100, 200, 30);

        root.getChildren().add(canvas);
        root.getChildren().add(rBar.getCanvas());  // HP bar on top
        StackPane.setAlignment(rBar.getCanvas(), Pos.TOP_LEFT);
        Tile[][] tiles = location.getTileArray();

        renderer.renderLocation(location, player, canvas, true);

        Scene gameScene = getScene(root, tiles);

        gameScene.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaY() > 0) {
                renderer.setCameraSize(-1);  // Zoom in
            } else if (event.getDeltaY() < 0) {
                renderer.setCameraSize(1); // Zoom out
            }

            renderer.renderLocation(location, player, canvas);
        });

        stage.setTitle("Игра");
        return gameScene;
    }

    private Scene getScene(StackPane root, Tile[][] tiles) {
        Scene gameScene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT);

        gameScene.setOnMousePressed(this::handleMousePress);
        gameScene.setOnMouseDragged(this::handleMouseDrag);
        gameScene.setOnMouseReleased(this::handleMouseRelease);

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    if (tiles[player.getX()][player.getY()-1].isPassable())
                        player.move(0, -1);  // Move up
                    break;
                case A:
                    if (tiles[player.getX()-1][player.getY()].isPassable())
                        player.move(-1, 0);  // Move left
                    break;
                case S:
                    if (tiles[player.getX()][player.getY()+1].isPassable())
                        player.move(0, 1);  // Move down
                    break;
                case D:
                    if (tiles[player.getX()+1][player.getY()].isPassable())
                        player.move(1, 0);   // Move right
                    break;
            }

            renderer.renderLocation(location, player, canvas, true);
        });
        return gameScene;
    }

    private void handleMousePress(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            isDragging = true;
            mouseStartX = event.getSceneX();
            mouseStartY = event.getSceneY();
        }
    }

    private void handleMouseDrag(MouseEvent event) {
        if (isDragging) {
            double dragX = event.getSceneX() - mouseStartX;
            double dragY = event.getSceneY() - mouseStartY;

            renderer.setCameraXY((float) (renderer.getCameraX() - dragX), (float) (renderer.getCameraY() - dragY));

            mouseStartX = event.getSceneX();
            mouseStartY = event.getSceneY();


            renderer.renderLocation(location, player, canvas);
        }
    }

    private void handleMouseRelease(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            isDragging = false;
        }
    }
}
