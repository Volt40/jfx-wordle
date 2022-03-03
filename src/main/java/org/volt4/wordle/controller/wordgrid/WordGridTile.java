package org.volt4.wordle.controller.wordgrid;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.type.Letter;

import java.io.IOException;

/**
 * A tile on the WordGrid.
 */
public class WordGridTile extends AnchorPane {

    // The letter displayed in this tile.
    private Letter letter;

    @FXML
    private ImageView letterImage;

    @FXML
    private ImageView lockIcon;

    /**
     * Constructs and loads a WordGridTile.
     */
    public WordGridTile() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/components/WordGridTile.fxml"));
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
     * Selects this tile.
     */
    public void select() {
        getStyleClass().clear();
        getStyleClass().add("selected-tile");
    }

    /**
     * De selects this tile.
     */
    public void deselect() {
        if (!getStyleClass().contains("selected-tile"))
            return;
        getStyleClass().clear();
        if (letter == Letter.EMPTY)
            getStyleClass().add("empty-tile");
        else
            getStyleClass().add("filled-tile");
    }

    /**
     * Returns the lock icon.
     * @return The lock icon.
     */
    public ImageView getLockIcon() {
        return lockIcon;
    }

    /**
     * Sets the letter of this tile (and updates the image).
     * @param letter The letter to update to.
     */
    public void setLetter(Letter letter) {
        letterImage.setImage(letter.getImage());
        this.letter = letter;
        if (letter == Letter.EMPTY) {
            getStyleClass().clear();
            getStyleClass().add("empty-tile");
        }
    }

    /**
     * Only changes the internal letter, not the image.
     * @param letter Letter to set.
     */
    public void setLetterInternal(Letter letter) {
        this.letter = letter;
    }

    /**
     * Returns the letter in this tile.
     * @return The letter in this tile.
     */
    public Letter getLetter() {
        return letter;
    }

    /**
     * Returns the ImageView that holds the letter in this tile.
     * @return The ImageView that holds the letter in this tile.
     */
    public ImageView getLetterImage() {
        return letterImage;
    }

}
