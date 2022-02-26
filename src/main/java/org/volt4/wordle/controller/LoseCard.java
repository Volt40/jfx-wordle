package org.volt4.wordle.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

/**
 * The card that displayed the correct word when you lose.
 */
public class LoseCard extends AnchorPane {

    @FXML
    private Text titleText;

    @FXML
    private Text word;

    /**
     * Constructs the lose card.
     */
    public LoseCard() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/LoseCard.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Recenter title text.
        titleText.setLayoutX(((getWidth() / 2) - (titleText.getLayoutBounds().getWidth() / 2)));
    }

    /**
     * Sets the correct word.
     * @param correctWord Word to set.
     */
    public void setCorrectWord(String correctWord) {
        word.setText(correctWord.toUpperCase());
        word.setLayoutX((getWidth() / 2) - (word.getLayoutBounds().getWidth() / 2));
    }

}
