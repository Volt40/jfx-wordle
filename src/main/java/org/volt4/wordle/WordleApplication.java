package org.volt4.wordle;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.WritableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Timer;
import java.util.TimerTask;

public class WordleApplication extends Application {

    // Wordle controller.
    private static WordleUIController controller;

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
        primaryStage.setResizable(false);
        primaryStage.show();
        WordleApplication.primaryStage = primaryStage;
    }

    /**
     * Returns the controller.
     * @return The controller.
     */
    public static WordleUIController getController() {
        return controller;
    }

    /**
     * Handles when a key is pressed.
     * @param key Key that is pressed.
     */
    public void handleKeyPress(String key) {
        if (key.equals("backspace"))
            controller.embeddedGrid().deleteLetter();
        else if (key.equals("enter"))
            controller.embeddedGrid().enterWord();
        else if ("abcdefghijklmnopqrstuvwxyz".indexOf(key) != -1)
            controller.embeddedGrid().inputLetter(key);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
