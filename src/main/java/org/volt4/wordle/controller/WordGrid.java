package org.volt4.wordle.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.volt4.wordle.AnimationManager;
import org.volt4.wordle.Letter;
import org.volt4.wordle.TileColor;
import org.volt4.wordle.WordLists;
import org.volt4.wordle.animation.RowReveal;
import org.volt4.wordle.animation.TileBounce;

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
        answer = WordLists.pickRandomAnswer(); // First answer.
    }

    /**
     * Resets the grid.
     */
    public void reset() {
        // Reset indices.
        currentRow = 0;
        currentColumn = 0;
        // Reset tiles.
        for (int i = 0; i < N_ROWS; i++)
            for (int j = 0; j < N_COLUMNS; j++)
                if (tiles[i][j].getLetter() != Letter.EMPTY)
                    AnimationManager.playTileFlipAnimation(i, j, TileColor.DARK_GREY, true);
        // Pick new answer.
        answer = WordLists.pickRandomAnswer();
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
        if (currentColumn != N_COLUMNS)
            return false;
        // Get the word inputted.
        String word = "";
        for (int i = 0; i < N_COLUMNS; i++)
            word += tiles[currentRow][i].getLetter().getLetter();
        // Check if the guess is correct.
        if (!WordLists.guessIsValid(word)) {
            AnimationManager.playRowShakeAnimation(currentRow);
            return false;
        }
        if (word.equals(answer)) {
            final int correctRow = currentRow;
            Platform.runLater(new Timeline(new KeyFrame(Duration.millis(RowReveal.ANIMATION_DURATION + TileBounce.ANIMATION_DURATION),e -> AnimationManager.playRowBounceAnimation(correctRow)))::play);
        }
        // Get the colors to flip to.
        TileColor[] colors = new TileColor[N_COLUMNS];
        for (int i = 0; i < N_COLUMNS; i++) {
            String letter = tiles[currentRow][i].getLetter().getLetter();
            colors[i] = TileColor.LIGHT_GREY;
            for (int j = 0; j < N_COLUMNS; j++)
                if (("" + answer.charAt(j)).equals(letter))
                    if (i == j) {
                        colors[i] = TileColor.GREEN;
                        break;
                    } else
                        colors[i] = TileColor.YELLOW;
        }
        // Create letters array.
        Letter[] letters = new Letter[N_COLUMNS];
        for (int i = 0; i < letters.length; i++)
            letters[i] = Letter.getMatch(word.substring(i, i + 1));
        // Play animation.
        AnimationManager.playRowRevealAnimation(currentRow, colors, letters);
        // Increment row.
        currentRow++;
        currentColumn = 0;
        return word.equals(answer);
    }

}
