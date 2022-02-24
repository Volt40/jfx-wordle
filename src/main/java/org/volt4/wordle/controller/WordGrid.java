package org.volt4.wordle.controller;

import javafx.scene.layout.GridPane;
import org.volt4.wordle.AnimationManager;
import org.volt4.wordle.Letter;

/**
 * Represents a wordgrid.
 */
public class WordGrid extends GridPane {

    // Number of rows and columns.
    public static final int N_ROWS = 6;
    public static final int N_COLUMNS = 5;

    // Tiles in this wordgrid.
    private WordGridTile[][] tiles;

    // Keeps track of where you are typing.
    private int currentRow;
    private int currentColumn;

    // Wordle answer.
    private String answer;

    /**
     * Constructs a WordGrid and set a parameter.
     */
    public WordGrid() {
        getStyleClass().add("word-grid");
        tiles = new WordGridTile[N_ROWS][N_COLUMNS];
        for (int i = 0; i < N_ROWS; i++)
            for (int j = 0; j < N_COLUMNS; j++) {
                tiles[i][j] = new WordGridTile();
                add(tiles[i][j], j, i);
            }
        currentRow = 0;
        currentColumn = 0;
        AnimationManager.initTileAnimations(tiles);
    }

    /**
     * Inputs the letter into the grid.
     * @param letter Letter to be inputted.
     */
    public void inputLetter(Letter letter) {
        if (currentColumn >= N_COLUMNS)
            return;
        AnimationManager.playTilePopulateAnimation(currentRow, currentColumn, letter);
        currentColumn++;
    }

    /**
     * Removed the last letter.
     */
    public void deleteLetter() {
        if (currentColumn > 0)
            currentColumn--;
        tiles[currentRow][currentColumn].setLetter(Letter.EMPTY);
    }

    /**
     * Attempts to enter the current inputted word.
     * @return True if the answer is correct.
     */
    public boolean enterWord() {
        // Make sure the word is the correct length.
        if (currentColumn != N_COLUMNS - 1)
            return false;
        // Get the word inputted.
        String word = "";
        for (int i = 0; i < N_COLUMNS; i++)
            word += tiles[currentRow][i].getLetter().getLetter();
        // Check if the guess is correct.
        //TODO REVEAL ANIMATION
        return false;
    }

}
