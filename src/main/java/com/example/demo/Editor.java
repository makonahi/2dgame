package com.example.demo;

import com.example.demo.gameobject.Location;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Arrays;

public class Editor {

    private Scene editorScene;

    private final JsonManager jman = new JsonManager();

    private Button getButton(TextField locationNameField, LocationGrid locationGrid) {
        Button saveButton = new Button("Сохранить локацию");

        saveButton.setOnAction(_ -> {
            String locationName = locationNameField.getText();
            if (locationName == null || locationName.trim().isEmpty()) {
                showErrorMessage("Ошибка ввода.", "Введите корректное имя локации.");
                return;
            }

            Location newLocation = new Location();
            newLocation.setTileArray(locationGrid.getNewLocationTileSet());
            System.out.println(Arrays.deepToString(locationGrid.getNewLocationTileSet()));
            newLocation.setFileName(locationNameField.getText() + ".json");
            newLocation.setSize(10);

            jman.writeJsonObject(newLocation.getFileName(), newLocation);
        });
        return saveButton;
    }

    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }


    public Scene getEditorScene(Stage stage, Scene mainMenuScene){
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        LocationGrid locationGrid = new LocationGrid(10, 10);
        TileTypeSelector selector = new TileTypeSelector(locationGrid);

        TextField locationNameField = new TextField();
        locationNameField.setPromptText("Введите имя локации...");

        Button saveButton = getButton(locationNameField, locationGrid);

        Button backButton = new Button("Главное меню");
        backButton.setOnAction(e -> stage.setScene(mainMenuScene));

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(saveButton, backButton);

        root.setLeft(locationNameField);
        root.setBottom(buttonLayout);
        root.setTop(selector);
        root.setCenter(locationGrid);

        stage.setTitle("Редактор");
        return scene;
    }
}
