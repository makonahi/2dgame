package com.example.demo;

import com.example.demo.gameobject.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Game {

    private static final JsonManager jman = new JsonManager();

    private double mouseStartX, mouseStartY;
    private boolean isDragging = false;

    private static final int CANVAS_WIDTH = 900;
    private static final int CANVAS_HEIGHT = 900;
    private static final int BASIC_TILE_SIZE = 100;
    private static final double DEFAULT_ZOOM_SCALE=0.4d;
    private static final double MAX_ZOOM_SCALE=2.0d;
    private static final double MIN_ZOOM_SCALE=0.2d;

    Scene gameScene;

    private GameRenderer renderer;
    private Player player = new Player(1, 1);
    private ResourceBar rBar;
    private Location location;
    private Canvas backgroundCanvas, entityCanvas;

    private double canvasOffsetX = 0;
    private double canvasOffsetY = 0;

    private double scale = 0.4;
    private final double zoomFactor = 0.1; // How much to zoom in/out per scroll

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

        if (Objects.isNull(location)) {
            location = jman.readJsonObject(locationFileName, Location.class);
            /// this is test stuff; to be removed
            Armor hArmor = jman.readJsonObject("heavyArmor.json", Armor.class);
            Armor lArmor = jman.readJsonObject("leatherArmor.json", Armor.class);
            Armor liArmor = jman.readJsonObject("lightArmor.json", Armor.class);
            ItemEntity ent = new ItemEntity(hArmor, 1, 1);
            ItemEntity ent1 = new ItemEntity(lArmor, 2, 1);
            ItemEntity ent2 = new ItemEntity(liArmor, 3, 1);

            location.addEntity(ent, ent1, ent2);
        }

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");
        root.setMinSize(CANVAS_WIDTH, CANVAS_HEIGHT);

        backgroundCanvas = new Canvas(location.getWidth()*BASIC_TILE_SIZE, location.getHeight()*BASIC_TILE_SIZE);
        entityCanvas = new Canvas(location.getWidth()*BASIC_TILE_SIZE, location.getHeight()*BASIC_TILE_SIZE);
        rBar = new ResourceBar(100, 200, 30);

        zoomCanvas(DEFAULT_ZOOM_SCALE);

        backgroundCanvas.setManaged(false);
        entityCanvas.setManaged(false);

        root.getChildren().addAll(backgroundCanvas, rBar.getCanvas(), entityCanvas);
        StackPane.setAlignment(rBar.getCanvas(), Pos.TOP_LEFT);

        renderer.renderLocation(backgroundCanvas, location);
        renderer.renderEntites(entityCanvas, location, player);
        gameScene = new Scene(root);

        gameScene.setOnMousePressed(this::handleMousePress);
        gameScene.setOnMouseDragged(this::handleMouseDrag);
        gameScene.setOnMouseReleased(this::handleMouseRelease);

        gameScene.setOnKeyPressed(event -> {
            int deltaX=0, deltaY=0;
            switch (event.getCode()) {
                case W -> deltaY=-1;
                case S -> deltaY=1;
                case A -> deltaX=-1;
                case D -> deltaX=1;
            }
            if (location.getTileArray()[player.getX()+deltaX][player.getY()+deltaY].isPassable()) {
                player.move(deltaX, deltaY);
                renderer.renderEntites(entityCanvas, location, player);
            }
        });

        gameScene.setOnScroll(event -> {
            if (event.getDeltaY() > 0) {
                zoomCanvas(zoomFactor);
            } else {
                zoomCanvas(-zoomFactor);
            }
        });

        stage.setTitle("Игра");
        return gameScene;
    }

    private void moveCanvas(double deltaX, double deltaY){
        canvasOffsetX += deltaX;
        canvasOffsetY += deltaY;

        setCanvasesLayoutXY(backgroundCanvas, entityCanvas);
    }

    private void zoomCanvas(double factor) {
        scale += factor;
        scale = Math.max(MIN_ZOOM_SCALE, Math.min(scale, MAX_ZOOM_SCALE));

        setCanvasesScale(backgroundCanvas, entityCanvas);
    }

    private void setCanvasesScale(Canvas... canvases){
        for (Canvas canv : canvases){
            canv.setScaleX(scale);
            canv.setScaleY(scale);
            canv.setLayoutX(canvasOffsetX * scale);
            canv.setLayoutY(canvasOffsetY * scale);
        }
    }

    private void setCanvasesLayoutXY(Canvas... canvases){
        for (Canvas canv : canvases){
            canv.setLayoutX(canvasOffsetX);
            canv.setLayoutY(canvasOffsetY);
        }
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

            moveCanvas(dragX, dragY);

            mouseStartX = event.getSceneX();
            mouseStartY = event.getSceneY();
        }
    }

    private void handleMouseRelease(MouseEvent event) {
        if (event.getButton() == MouseButton.MIDDLE) {
            isDragging = false;
        }
    }
}
