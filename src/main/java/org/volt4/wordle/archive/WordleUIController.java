package org.volt4.wordle.archive;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.WordleApplication;
import org.volt4.wordle.archive.animation.KeyboardInAnimation;
import org.volt4.wordle.archive.animation.KeyboardOutAnimation;
import org.volt4.wordle.archive.animation.ResetAnimation;
import org.volt4.wordle.archive.KeyboardUIController;
import org.volt4.wordle.archive.WordGridUIController;
import org.volt4.wordle.controller.Keyboard;

import java.io.IOException;

/**
 * Main controller class for the application.
 */
public class WordleUIController extends AnchorPane {

    // WordGrid contained in this controller.
    private WordGridUIController wordGridUIController;

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
        wordGridUIController = new WordGridUIController();
        wordGridUIController.setLayoutX(0);
        wordGridUIController.setLayoutY(30);
        // Create the keyboard.
        keyboard = new KeyboardUIController(wordGridUIController);
        keyboard.setLayoutX(0);
        keyboard.setLayoutY(450);
        // Add the grid and keyboard to the layout.
        //WordGrid wg = new WordGrid();
        //wg.setLayoutY(30);
        Keyboard kb = new Keyboard();
        kb.setLayoutY(30);
        getChildren().addAll(kb);
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
    public WordGridUIController embeddedGrid() {
        return wordGridUIController;
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
        wordGridUIController.reset();
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

    /**
     * Consumers for the menubar.
     */
    @FXML void onXPressed(MouseEvent event) {event.consume();}
    @FXML void onXDragged(MouseEvent event) {event.consume();}
    @FXML void onResetPressed(MouseEvent event) {event.consume();}
    @FXML void onResetDragged(MouseEvent event) {event.consume();}
    @FXML void onKeyboardPressed(MouseEvent event) {event.consume();}
    @FXML void onKeyboardDragged(MouseEvent event) {event.consume();}

}
