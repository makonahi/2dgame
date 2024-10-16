package com.example.demo;

import com.example.demo.gameobject.Location;
import com.example.demo.gameobject.Tile;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;

public class LocationGrid extends GridPane {

    private JsonManager jman = new JsonManager();

    private Tile selectedTileType = jman.readJsonObject("floor.json", Tile.class);
    private Image img;
    Tile[][] newTileSet;

    public LocationGrid(int width, int height) {
        newTileSet = new Tile[10][10];

        updateTexture();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Button tileButton = new Button();
                tileButton.setPrefSize(50, 50);  // Size of each tile
                tileButton.setStyle("-fx-background-color: black; -fx-background-radius: 0;");

                tileButton.setBorder(new Border(new BorderStroke(Paint.valueOf("#FFFFFF"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                tileButton.setPadding(new Insets(-1, -1, -1, -1));
                int finalY = y;
                int finalX = x;
                tileButton.setOnAction(e -> {
                    ImageView iv = new ImageView(img);
                    iv.setFitWidth(50);
                    iv.setFitHeight(50);
                    iv.setSmooth(true);
                    iv.setPreserveRatio(true);
                    tileButton.setGraphic(iv);
                    newTileSet[finalX][finalY]=selectedTileType;
                });

                add(tileButton, x, y);
            }
        }
    }

    public void setSelectedTileType(Tile tileType) {
        this.selectedTileType = tileType;
        updateTexture();
    }

    private void updateTexture(){
        InputStream is = getClass().getResourceAsStream("textures/"+selectedTileType.getFileTextureName());
        img = new Image(is, 50, 50, false, false);
    }

    public Tile[][] getNewLocationTileSet() {
        return newTileSet;
    }
}