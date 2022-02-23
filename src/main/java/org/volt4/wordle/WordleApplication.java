package org.volt4.wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.volt4.wordle.archive.WordleUIController;
import org.volt4.wordle.controller.Wordle;

/**
 * Wordle main application class.
 */
public class WordleApplication extends Application {

    // Wordle controller.
    private static WordleUIController controller;

    // The state this is on.
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        WordLists.load();
        controller = new WordleUIController();
        Scene scene = new Scene(new Wordle());
        scene.getStylesheets().add("wordlestyle.css");
        scene.setFill(Color.TRANSPARENT);
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode().getName().toLowerCase()));
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
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
//        if (key.equals("backspace"))
//            controller.embeddedGrid().deleteLetter();
//        else if (key.equals("enter"))
//            controller.embeddedGrid().enterWord();
//        else if ("abcdefghijklmnopqrstuvwxyz".indexOf(key) != -1)
//            controller.embeddedGrid().inputLetter(key);
        AnimationManager.playTilePopulateAnimation(0, 0, Letter.getMatch(key));
    }

    public static void main(String[] args) {
        launch(args);
    }

}
