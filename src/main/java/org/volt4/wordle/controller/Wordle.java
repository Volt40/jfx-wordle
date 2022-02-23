package org.volt4.wordle.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.AnimationManager;
import org.volt4.wordle.WordleApplication;

import java.io.IOException;

/**
 * A Wordle Game.
 */
public class Wordle extends AnchorPane {

    @FXML
    private ImageView resetImage;

    // WordGrid the game is played on.
    private WordGrid wordgrid;

    // Optional keyboard.
    private Keyboard keyboard;

    // Used for animation.
    private boolean keyboardHidden;

    /**
     * Constructs a Wordle game.
     */
    public Wordle() {
        // Load the layout.
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/Wordle.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Setup animations.
        keyboardHidden = true;
        AnimationManager.initKeyboardAnimations();
        AnimationManager.initResetAnimations(resetImage);
        // Setup offsets.
        offset = new double[] {0, 0};
        // Construct keyboard and grid.
        wordgrid = new WordGrid();
        keyboard = new Keyboard();
        // Set constraints.
        wordgrid.setLayoutY(30);
        keyboard.setLayoutY(450);
        // Add children.
        getChildren().addAll(wordgrid, keyboard);
    }

    @FXML
    void onClose(MouseEvent event) {
        Platform.exit();
    }

    // Used for drag & drop.
    private double[] offset;

    @FXML
    void onDragBarPressed(MouseEvent event) {
        offset[0] = event.getSceneX();
        offset[1] = event.getSceneY();
    }

    @FXML
    void onDragBarDragged(MouseEvent event) {
        System.out.println("dragged");
        WordleApplication.primaryStage.setX(event.getScreenX() - offset[0]);
        WordleApplication.primaryStage.setY(event.getScreenY() - offset[1]);
    }

    @FXML
    void onKeyboard(MouseEvent event) {
        if (keyboardHidden)
            AnimationManager.playKeyboardShowAnimation();
        else
            AnimationManager.playKeyboardHideAnimation();
        keyboardHidden = !keyboardHidden;
    }

    @FXML
    void onReset(MouseEvent event) {
        AnimationManager.playResetIconSpinAnimation();
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
