package org.volt4.wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.volt4.wordle.controller.Wordle;

/**
 * Wordle main application class.
 */
public class WordleApplication extends Application {

    // Wordle controller.
    private static Wordle wordle;

    // The state this is on.
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        WordLists.load();
        Scene scene = new Scene(wordle = new Wordle());
        scene.getStylesheets().add("wordlestyle.css");
        scene.setFill(Color.TRANSPARENT);
        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode().getName().toLowerCase()));
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("icons/wordle.png"));
        primaryStage.show();
        WordleApplication.primaryStage = primaryStage;
    }

    /**
     * Handles when a key is pressed.
     * @param key Key that is pressed.
     */
    public void handleKeyPress(String key) {
        if (key.equals("backspace"))
            wordle.deleteLetter();
        else if (key.equals("enter"))
            wordle.enterWord();
        else if ("abcdefghijklmnopqrstuvwxyz".indexOf(key) != -1) {
            wordle.inputLetter(Letter.getMatch(key));
            AnimationManager.playKeyPulseAnimation(Letter.getMatch(key).ID());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
