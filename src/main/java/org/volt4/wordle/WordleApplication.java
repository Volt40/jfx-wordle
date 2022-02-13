package org.volt4.wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WordleApplication extends Application {

    // Wordle controller.
    private WordleUIController controller;

    // The state this is on.
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        WordLists.load();
        Scene scene = new Scene(controller = new WordleUIController());
        scene.getStylesheets().add("wordlestyle.css");
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode().getName().toLowerCase()));
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        WordleApplication.primaryStage = primaryStage;
    }

    /**
     * Handles when a key is pressed.
     * @param key Key that is pressed.
     */
    public void handleKeyPress(String key) {
        if (key.equals("backspace")) {
            controller.embeddedGrid().deleteLetter();
        }else if (key.equals("enter"))
            controller.embeddedGrid().enterWord();
        else if ("abcdefghijklmnopqrstuvwxyz".indexOf(key) != -1)
            controller.embeddedGrid().inputLetter(key);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
