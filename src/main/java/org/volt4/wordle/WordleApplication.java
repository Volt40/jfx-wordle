package org.volt4.wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.volt4.wordle.animation.AnimationManager;
import org.volt4.wordle.controller.Wordle;
import org.volt4.wordle.controller.config.Settings;
import org.volt4.wordle.type.Letter;
import org.volt4.wordle.type.WordleTheme;

/**
 * Wordle main application class.
 */
public class WordleApplication extends Application {

    // Wordle controller.
    private static Wordle wordle;

    // The stage this is on.
    public static Stage primaryStage;

    // Wordle scene.
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Settings.importSettings();
        WordLists.load();
        scene = new Scene(new AnchorPane()); // Workaround, might remove later.
        scene.getStylesheets().add("stylesheets/wordlestyle.css");
        scene.getStylesheets().add(WordleTheme.DARK.pathToCss());
        scene.getStylesheets().add(WordleTheme.LOW_CONTRAST.pathToCss());
        scene.setRoot(wordle = new Wordle());
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
     * Returns the wordle controller.
     * @return The wordle controller.
     */
    public static Wordle getWordle() {
        return wordle;
    }

    /**
     * Inits the wordle theme.
     * @param theme Theme to init.
     */
    public static void initTheme(WordleTheme theme) {
        scene.getStylesheets().set(theme.getStylesheetIndex(), theme.pathToCss());
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
