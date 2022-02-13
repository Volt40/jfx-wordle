package org.volt4.wordle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.animations.KeyboardInAnimation;
import org.volt4.wordle.animations.KeyboardOutAnimation;
import org.volt4.wordle.animations.ResetAnimation;

import java.io.IOException;

/**
 * Main controller class for the application.
 */
public class WordleUIController extends AnchorPane {

    // WordGrid contained in this controller.
    private WordGrid wordGrid;

    // Keyboard contained in this controller.
    private KeyboardUIController keyboard;

    // Animations for the keyboard.
    private KeyboardInAnimation inAnimation;
    private KeyboardOutAnimation outAnimation;

    // Animation for the reset.
    private ResetAnimation resetAnimation;

    // True if the keyboard is visible.
    private boolean keyboardOut;

    @FXML
    private ImageView resetImage;

    // Used for drag & drop.
    private double[] offset;

    /**
     * Constructs a Controller for the application.
     */
    public WordleUIController() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/Wordle.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        keyboardOut = false;
        // Create the WordGrid.
        wordGrid = new WordGrid();
        wordGrid.setLayoutX(0);
        wordGrid.setLayoutY(30);
        // Create the keyboard.
        keyboard = new KeyboardUIController(wordGrid);
        keyboard.setLayoutX(0);
        keyboard.setLayoutY(450);
        // Add the grid and keyboard to the layout.
        getChildren().addAll(wordGrid, keyboard);
        // Contruct animations.
        inAnimation = new KeyboardInAnimation();
        outAnimation = new KeyboardOutAnimation();
        resetAnimation = new ResetAnimation(resetImage);
        // Setup drag
        offset = new double[] {0, 0};
    }

    /**
     * Returns the WordGrid embedded in this controller.
     * @return The WordGrid embedded in this controller.
     */
    public WordGrid embeddedGrid() {
        return wordGrid;
    }

    /**
     * Returns the keyboard.
     * @return The Keyboard.
     */
    public KeyboardUIController getKeyboard() {
        return keyboard;
    }

    @FXML
    void onClose(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void onReset(MouseEvent event) {
        wordGrid.reset();
        keyboard.reset();
        resetAnimation.runAnimation();
    }

    @FXML
    void onKeyboard(MouseEvent event) {
        if (keyboardOut)
            inAnimation.runAnimation();
        else
            outAnimation.runAnimation();
        keyboardOut = !keyboardOut;
    }

    @FXML
    void onDragBarPressed(MouseEvent event) {
        offset[0] = event.getSceneX();
        offset[1] = event.getSceneY();
    }

    @FXML
    void onDragBarDragged(MouseEvent event) {
        WordleApplication.primaryStage.setX(event.getScreenX() - offset[0]);
        WordleApplication.primaryStage.setY(event.getScreenY() - offset[1]);
    }

}
