package org.volt4.wordle.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.Letter;

import java.io.IOException;

/**
 * A tile on the WordGrid.
 */
public class WordGridTile extends AnchorPane {

    // The letter displayed in this tile.
    private Letter letter;

    @FXML
    private ImageView letterImage;

    /**
     * Constructs and loads a WordGridTile.
     */
    public WordGridTile() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/WordGridTile.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLetter(Letter.EMPTY); // Empty letter when constructed.
    }

    /**
     * Sets the letter of this tile (and updates the image).
     * @param letter The letter to update to.
     */
    public void setLetter(Letter letter) {
        letterImage.setImage(letter.getImage());
        this.letter = letter;
    }

    /**
     * Returns the letter in this tile.
     * @return The letter in this tile.
     */
    public Letter getLetter() {
        return letter;
    }

}
