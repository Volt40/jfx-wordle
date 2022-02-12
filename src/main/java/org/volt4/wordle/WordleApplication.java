package org.volt4.wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class WordleApplication extends Application {

    private WordGrid grid;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(grid = new WordGrid());
        scene.getStylesheets().add("wordlestyle.css");
        scene.setOnKeyPressed(e -> handleKeyPress(e.getText()));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles when a key is pressed.
     * @param key Key that is pressed.
     */
    public void handleKeyPress(String key) {
        grid.f(key);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
