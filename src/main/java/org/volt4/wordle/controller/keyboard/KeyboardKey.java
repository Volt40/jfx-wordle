package org.volt4.wordle.controller.keyboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.AnimationManager;
import org.volt4.wordle.Letter;

import java.io.IOException;

/**
 * A key on the keyboard.
 */
public class KeyboardKey extends AnchorPane {

    @FXML
    private ImageView letterImage;

    // Runnable to run when this is clicked.
    private Runnable onClick;

    // Letter of this key.
    private Letter letter;

    /**
     * Creates a keyboard key with the given letter.
     * @param letter Letter of this key.
     */
    public KeyboardKey(Letter letter, Runnable onClick) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/KeyboardKey.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        letterImage.setImage(letter.getImage());
        this.onClick = onClick;
        setPrefHeight(40);
        if (letter == Letter.ENTER || letter == Letter.BACKSPACE) {
            setPrefWidth(52.5);
            letterImage.setFitHeight(30);
            letterImage.setFitWidth(30);
            AnchorPane.setTopAnchor(letterImage, letter == Letter.ENTER ? 7d : 5d);
            AnchorPane.setLeftAnchor(letterImage, 10d);
        } else
            setPrefWidth(35);
        this.letter = letter;
    }

    /**
     * Returns the letter of this key.
     * @return The letter of this key.
     */
    public Letter getLetter() {
        return letter;
    }

    @FXML
    void onPress(MouseEvent event) {
        onClick.run();
    }

    @FXML
    void onMouseEnter(MouseEvent event) {
        AnimationManager.playKeyGrowAnimation(letter.ID());
    }

    @FXML
    void onMouseExit(MouseEvent event) {
        AnimationManager.playKeyShrinkAnimation(letter.ID());
    }

}
