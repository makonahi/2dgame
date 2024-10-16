package com.example.demo;

import com.example.demo.gameobject.Tile;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.util.ArrayList;

public class TileTypeSelector extends HBox {

    private JsonManager jman = new JsonManager();

    private ComboBox<Tile> tileTypeComboBox;
    private final ArrayList<Tile> allTiles;

    public TileTypeSelector(LocationGrid locationGrid) {
        allTiles = jman.readJsonObjectsOfType(Tile.class);
        tileTypeComboBox = new ComboBox<>();

        tileTypeComboBox.getItems().addAll(allTiles);
        tileTypeComboBox.setValue(allTiles.getFirst());  // Default selection

        // Update the selected tile type in the grid when a new tile type is chosen
        tileTypeComboBox.setOnAction(e -> {
            locationGrid.setSelectedTileType(tileTypeComboBox.getValue());
        });

        getChildren().add(tileTypeComboBox);
    }
}