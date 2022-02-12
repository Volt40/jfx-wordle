package org.volt4.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class WordGrid extends GridPane {

    /*
     * Listed here are all the AnchorPane objects containing the letters.
     */
    @FXML private AnchorPane wordleGrid00;
    @FXML private AnchorPane wordleGrid10;
    @FXML private AnchorPane wordleGrid20;
    @FXML private AnchorPane wordleGrid30;
    @FXML private AnchorPane wordleGrid40;
    @FXML private AnchorPane wordleGrid01;
    @FXML private AnchorPane wordleGrid11;
    @FXML private AnchorPane wordleGrid21;
    @FXML private AnchorPane wordleGrid31;
    @FXML private AnchorPane wordleGrid41;
    @FXML private AnchorPane wordleGrid02;
    @FXML private AnchorPane wordleGrid12;
    @FXML private AnchorPane wordleGrid22;
    @FXML private AnchorPane wordleGrid32;
    @FXML private AnchorPane wordleGrid42;
    @FXML private AnchorPane wordleGrid03;
    @FXML private AnchorPane wordleGrid13;
    @FXML private AnchorPane wordleGrid23;
    @FXML private AnchorPane wordleGrid33;
    @FXML private AnchorPane wordleGrid43;
    @FXML private AnchorPane wordleGrid04;
    @FXML private AnchorPane wordleGrid14;
    @FXML private AnchorPane wordleGrid24;
    @FXML private AnchorPane wordleGrid34;
    @FXML private AnchorPane wordleGrid44;
    @FXML private AnchorPane wordleGrid05;
    @FXML private AnchorPane wordleGrid15;
    @FXML private AnchorPane wordleGrid25;
    @FXML private AnchorPane wordleGrid35;
    @FXML private AnchorPane wordleGrid45;

    /*
     * Listed here are all the ImageView objects that display the letters.
     */
    @FXML private ImageView wordleGridLetter00;
    @FXML private ImageView wordleGridLetter10;
    @FXML private ImageView wordleGridLetter20;
    @FXML private ImageView wordleGridLetter30;
    @FXML private ImageView wordleGridLetter40;
    @FXML private ImageView wordleGridLetter01;
    @FXML private ImageView wordleGridLetter11;
    @FXML private ImageView wordleGridLetter21;
    @FXML private ImageView wordleGridLetter31;
    @FXML private ImageView wordleGridLetter41;
    @FXML private ImageView wordleGridLetter02;
    @FXML private ImageView wordleGridLetter12;
    @FXML private ImageView wordleGridLetter22;
    @FXML private ImageView wordleGridLetter32;
    @FXML private ImageView wordleGridLetter42;
    @FXML private ImageView wordleGridLetter03;
    @FXML private ImageView wordleGridLetter13;
    @FXML private ImageView wordleGridLetter23;
    @FXML private ImageView wordleGridLetter33;
    @FXML private ImageView wordleGridLetter43;
    @FXML private ImageView wordleGridLetter04;
    @FXML private ImageView wordleGridLetter14;
    @FXML private ImageView wordleGridLetter24;
    @FXML private ImageView wordleGridLetter34;
    @FXML private ImageView wordleGridLetter44;
    @FXML private ImageView wordleGridLetter05;
    @FXML private ImageView wordleGridLetter15;
    @FXML private ImageView wordleGridLetter25;
    @FXML private ImageView wordleGridLetter35;
    @FXML private ImageView wordleGridLetter45;

    /**
     * Constructs a WordGrid object. Loads WordGrid.fxml.
     */
    public WordGrid() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/WordGrid.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Represents a cell in the WordGrid.
     */
    private static class Cell {

        // AnchorPane containing the letter.
        private AnchorPane gridCell;

        // ImageView displaying the letter.
        private ImageView letter;

        public Cell(AnchorPane gridCell, ImageView letter) {
            this.gridCell = gridCell;
            this.letter = letter;
            // Set the letter to blank.
            letter.setImage(Letters.EMPTY.getImage());
        }

    }

}
