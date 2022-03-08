package org.volt4.wordle.controller.wordgrid;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.volt4.wordle.animation.AnimationManager;
import org.volt4.wordle.type.Letter;
import org.volt4.wordle.type.Hint;

/**
 * Represents a wordgrid.
 */
public class WordGrid extends GridPane {

    // Number of rows and columns.
    public static int N_ROWS = 6;
    public static int N_COLUMNS = 5;

    // Tiles in this wordgrid.
    private WordGridTile[][] tiles;

    // Locked tiles in the current game. Used in hard mode.
    private boolean[][] lockedTiles;

    /**
     * Constructs a WordGrid and set a parameter.
     */
    public WordGrid(int N_ROWS, int N_COLUMNS) {
        this.N_ROWS = N_ROWS;
        this.N_COLUMNS = N_COLUMNS;
        lockedTiles = new boolean[N_ROWS][N_COLUMNS];
        getStyleClass().add("word-grid");
        tiles = new WordGridTile[N_ROWS][N_COLUMNS];
        for (int i = 0; i < N_ROWS; i++)
            for (int j = 0; j < N_COLUMNS; j++) {
                tiles[i][j] = new WordGridTile();
                add(tiles[i][j], j, i);
            }
        // Create clipping pane.
        Rectangle clip = new Rectangle(70 * N_COLUMNS, 70 * N_ROWS);
        clip.setLayoutX(getLayoutX());
        clip.setLayoutY(getLayoutY());
        setClip(clip);
        AnimationManager.initTileAnimations(tiles);
    }

    /**
     * Resets the grid.
     */
    public void reset() {
        // Reset tiles.
        for (int i = 0; i < N_ROWS; i++)
            for (int j = 0; j < N_COLUMNS; j++)
                if (tiles[i][j].getLetter() != Letter.EMPTY)
                    AnimationManager.playTileFlipAnimation(i, j, Hint.DARK_GREY, true);
    }

    /**
     * Sets the selected row and column.
     * @param row Row to be selected.
     * @param column Column to be selected.
     */
    public void setSelected(int row, int column) {
        for (WordGridTile tile : tiles[row])
            tile.deselect();
        tiles[row][column].select();
    }

    /**
     * Clears the selected row.
     * @param row Row to clear.
     */
    public void clearRowSelection(int row) {
        for (WordGridTile tile : tiles[row])
            tile.deselect();
    }

    /**
     * Sets the Letter of the given row and column.
     * @param letter Letter to set.
     * @param row Row of the letter to set.
     * @param column Column of the letter to set.
     */
    public void setLetter(Letter letter, int row, int column) {
        AnimationManager.playTilePopulateAnimation(row, column, letter);
    }

    /**
     * Returns the letter at the given position in the grid.
     * @param row Row of the letter to return.
     * @param column Column of the letter to return.
     * @return Letter at position (row, column).
     */
    public Letter getLetter(int row, int column) {
        return tiles[row][column].getLetter();
    }

    /**
     * Deletes the letter of the given row and column.
     * @param row Row of the letter to delete.
     * @param column Column of the letter to delete.
     */
    public void deleteLetter(int row, int column) {
        tiles[row][column].setLetter(Letter.EMPTY);
    }

    /**
     * Returns the word from the given row.
     * @param row Row of the word to build.
     * @return The word from the given row.
     */
    public String getWord(int row) {
        if (row >= N_ROWS)
            return new String(new char[N_COLUMNS]).replace('\0', ' ');
        String word = "";
        for (int i = 0; i < N_COLUMNS; i++)
            if (tiles[row][i].getLetter() != Letter.EMPTY)
                word += tiles[row][i].getLetter().getLetter();
            else
                word += " ";
        return word;
    }

    /**
     * Returns the String complex of the word grid.
     * @return The String complex of the word grid.
     */
    public String getComplex() {
        String complexColor = "";
        String complexLetter = "";
        for (int i = 0; i < N_ROWS && tiles[i][0].getLetter() != Letter.EMPTY; i++) {
            for (int j = 0; j < N_COLUMNS; j++) {
                complexColor += tiles[i][j].getStyleClass().get(0).equals(Hint.GREEN.getTileStyleClassName()) ? "g" : tiles[i][j].getStyleClass().get(0).equals(Hint.YELLOW.getTileStyleClassName()) ? "y" : "-";
                complexLetter += tiles[i][j].getLetter().getLetter();
            }
            complexColor += ",";
            complexLetter += ",";
        }
        return complexColor.substring(0, complexColor.length() - 1) + ":" + complexLetter.substring(0, complexLetter.length() - 1);
    }

    /*
     * ====================== DEPRECATED SECTION ======================
     * This will be kept commented out for now for future reference.
     * // TODO: remove this.
     */
//
//    /**
//     * Inputs the letter into the grid.
//     * @param letter Letter to be inputted.
//     */
//    @Deprecated
//    public void inputLetter(Letter letter) {
//        if (hasWon)
//            return;
//        if (currentColumn >= N_COLUMNS)
//            return;
//        AnimationManager.playTilePopulateAnimation(currentRow, currentColumn, letter);
//        currentColumn++;
//        if (SettingsScreen.HelpfulKeyboard && currentColumn != 5)
//            WordleApplication.getWordle().getKeyboard().updateSmartKeyboard(buildWord(), currentColumn);
//        else if (SettingsScreen.HelpfulKeyboard && currentColumn == 5)
//            WordleApplication.getWordle().getKeyboard().setAllKeysDisabled(true);
//    }
//
//    /**
//     * Removed the last letter.
//     */
//    @Deprecated
//    public void deleteLetter() {
//        if (hasWon)
//            return;
//        if (currentColumn > 0)
//            currentColumn--;
//        tiles[currentRow][currentColumn].setLetter(Letter.EMPTY);
//        if (SettingsScreen.HelpfulKeyboard)
//            WordleApplication.getWordle().getKeyboard().updateSmartKeyboard(buildWord(), currentColumn);
//    }
//
//    /**
//     * Attempts to enter the current inputted word.
//     * @return True if the answer is correct.
//     */
//    @Deprecated
//    public boolean enterWord() {
//        if (hasWon)
//            return true;
//        if (hasLost)
//            return false;
//        // Make sure the word is the correct length.
//        if (currentColumn != N_COLUMNS)
//            return false;
//        // Get the word inputted.
//        String word = "";
//        for (int i = 0; i < N_COLUMNS; i++)
//            word += tiles[currentRow][i].getLetter().getLetter();
//        // Check if the guess is correct.
//        if (!WordLists.guessIsValid(word)) {
//            AnimationManager.playRowShakeAnimation(currentRow);
//            return false;
//        }
//        if (word.equals(answer)) {
//            AnimationManager.playRowBounceAnimation(currentRow, RowReveal.ANIMATION_DURATION + TileFlip.ANIMATION_DURATION);
//            AnimationManager.playRowDoubleFlipAnimation(currentRow, RowReveal.ANIMATION_DURATION + TileFlip.ANIMATION_DURATION + RowBounce.ANIMATION_DURATION + TileBounce.ANIMATION_DURATION);
//            hasWon = true;
//        }
//        // Get the colors to flip to.
//        Hint[] colors = new Hint[N_COLUMNS];
//        for (int i = 0; i < N_COLUMNS; i++) {
//            String letter = tiles[currentRow][i].getLetter().getLetter();
//            colors[i] = Hint.LIGHT_GREY;
//            for (int j = 0; j < N_COLUMNS; j++)
//                if (("" + answer.charAt(j)).equals(letter))
//                    if (i == j) {
//                        colors[i] = Hint.GREEN;
//                        break;
//                    } else
//                        colors[i] = Hint.YELLOW;
//        }
//        // Create letters array.
//        Letter[] letters = new Letter[N_COLUMNS];
//        for (int i = 0; i < letters.length; i++)
//            letters[i] = Letter.getMatch(word.substring(i, i + 1));
//        // Play animation.
//        AnimationManager.playRowRevealAnimation(currentRow, colors, letters);
//        // Increment row.
//        currentRow++;
//        currentColumn = 0;
//        if (currentRow == N_ROWS && !hasWon) {
//            // Game has been lost.
//            hasLost = true;
//            AnimationManager.playLoseCardShowAnimation(answer, RowReveal.ANIMATION_DURATION + TileFlip.ANIMATION_DURATION);
//        }
//        if (SettingsScreen.HelpfulKeyboard)
//            WordleApplication.getWordle().getKeyboard().setAllKeysDisabled(false);
//        return word.equals(answer);
//    }
//
//    /**
//     * Builds the current word in the grid.
//     * @return The current word in the grid.
//     */
//    @Deprecated
//    private String buildWord() {
//        String word = "";
//        for (int i = 0; i < N_COLUMNS; i++)
//            if (tiles[currentRow][i].getLetter() != Letter.EMPTY)
//                word += tiles[currentRow][i].getLetter().getLetter();
//            else
//                word += " ";
//        return word;
//    }

}
