package org.volt4.wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WordleApplication extends Application {

    // Wordle grid.
    private WordGrid grid;

    @Override
    public void start(Stage primaryStage) throws Exception {
        WordLists.load();
        Scene scene = new Scene(grid = new WordGrid());
        scene.getStylesheets().add("wordlestyle.css");
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode().getName().toLowerCase()));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Handles when a key is pressed.
     * @param key Key that is pressed.
     */
    public void handleKeyPress(String key) {
        if (key.equals("backspace")) {
            grid.deleteLetter();
            System.out.println("backspace");
        }else if (key.equals("enter"))
            grid.enterWord();
        else if ("abcdefghijklmnopqrstuvwxyz".indexOf(key) != -1)
            grid.inputLetter(key);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
