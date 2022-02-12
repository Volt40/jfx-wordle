package org.volt4.wordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class WordGrid extends GridPane {

    // Keeps track of where you are typing.
    private int currentRow;
    private int currentColumn;

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

    /*
     * Grid containing all the cells.
     */
    private Cell[][] grid;

    public void reset() {
        currentRow = 0;
        currentColumn = 0;
        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[i].length; j++)
                grid[i][j].clear();
    }

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
        // Construct the grid.
        grid = new Cell[][] {
                {new Cell(wordleGrid00, wordleGridLetter00), new Cell(wordleGrid10, wordleGridLetter10), new Cell(wordleGrid20, wordleGridLetter20), new Cell(wordleGrid30, wordleGridLetter30), new Cell(wordleGrid40, wordleGridLetter40)},
                {new Cell(wordleGrid01, wordleGridLetter01), new Cell(wordleGrid11, wordleGridLetter11), new Cell(wordleGrid21, wordleGridLetter21), new Cell(wordleGrid31, wordleGridLetter31), new Cell(wordleGrid41, wordleGridLetter41)},
                {new Cell(wordleGrid02, wordleGridLetter02), new Cell(wordleGrid12, wordleGridLetter12), new Cell(wordleGrid22, wordleGridLetter22), new Cell(wordleGrid32, wordleGridLetter32), new Cell(wordleGrid42, wordleGridLetter42)},
                {new Cell(wordleGrid03, wordleGridLetter03), new Cell(wordleGrid13, wordleGridLetter13), new Cell(wordleGrid23, wordleGridLetter23), new Cell(wordleGrid33, wordleGridLetter33), new Cell(wordleGrid43, wordleGridLetter43)},
                {new Cell(wordleGrid04, wordleGridLetter04), new Cell(wordleGrid14, wordleGridLetter14), new Cell(wordleGrid24, wordleGridLetter24), new Cell(wordleGrid34, wordleGridLetter34), new Cell(wordleGrid44, wordleGridLetter44)},
                {new Cell(wordleGrid05, wordleGridLetter05), new Cell(wordleGrid15, wordleGridLetter15), new Cell(wordleGrid25, wordleGridLetter25), new Cell(wordleGrid35, wordleGridLetter35), new Cell(wordleGrid45, wordleGridLetter45)},
        };
        currentRow = 0;
        currentColumn = 0;
    }

    /**
     * Inputs the given letter into the grid.
     * @param letter Letter to be inputted.
     */
    public void inputLetter(String letter) {
        if (currentColumn > 4)
            return;
        grid[currentRow][currentColumn].setLetter(letter);
        currentColumn++;
    }

    /**
     * Deletes the last letter.
     */
    public void deleteLetter() {
        if (currentColumn <= 0)
            return;
        currentColumn--;
        grid[currentRow][currentColumn].clear();
    }

    /**
     * Represents a cell in the WordGrid.
     */
    public static class Cell {

        // AnchorPane containing the letter.
        private AnchorPane gridCell;

        // ImageView displaying the letter.
        private ImageView letter;

        // Animations
        private PopulateAnimation populateCell;
        private FlipAnimation flip;

        public Cell(AnchorPane gridCell, ImageView letter) {
            this.gridCell = gridCell;
            this.letter = letter;
            // Set the letter to blank.
            letter.setImage(Letters.EMPTY.getImage());
            // Create animations.
            populateCell = new PopulateAnimation(this);
            flip = new FlipAnimation(this);
        }

        /**
         * Clears the cell.
         */
        public void clear() {
            gridCell.getStyleClass().clear();
            gridCell.getStyleClass().add("empty-cells");
            letter.setImage(Letters.EMPTY.getImage());
        }

        /**
         * Returns the root of this object.
         * @return The root of this object.
         */
        public AnchorPane getRoot() {
            return gridCell;
        }

        /**
         * Runs the animation that populates this cell.
         * @param letter Letter to populate.
         */
        public void setLetter(String letter) {
            populateCell.runAnimation(Letters.getMatch(letter));
        }

        /**
         * Flips the cell to the given color.
         * @param color Color to flip to.
         */
        public void flip(FlipAnimation.Colors color) {
            flip.runAnimation(color);
        }

        /**
         * Sets the letter image of this cell.
         * @param letter Letter to set.
         */
        public void setLetterInternal(Letters letter) {
            this.letter.setImage(letter.getImage());
        }

    }

}
