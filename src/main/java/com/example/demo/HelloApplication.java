package com.example.demo;

import com.example.demo.gameobject.Armor;
import com.example.demo.gameobject.Item;
import com.example.demo.gameobject.ItemEntity;
import com.example.demo.procedural.generator.LevelGenerator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        VBox menuLayout = new VBox(10);
        Button loadEditorButton = new Button("Редактор");
        Button loadGameButton = new Button("Игра");

        Scene mainMenuScene = new Scene(menuLayout, 300, 200);

        loadEditorButton.setOnAction(_ -> {
            Editor editor = new Editor();
            Scene editorScene = editor.getEditorScene(primaryStage, mainMenuScene);
            primaryStage.setScene(editorScene);
        });

        loadGameButton.setOnAction(e -> {
            Game game = new Game();
                Scene gameScene = game.getGameScene(primaryStage, "devRoom.json");  // Load the location from file
            primaryStage.setScene(gameScene);
        });

        menuLayout.getChildren().addAll(loadEditorButton, loadGameButton);

        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}