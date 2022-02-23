package org.volt4.wordle.controller;

import javafx.scene.layout.GridPane;
import org.volt4.wordle.AnimationManager;

/**
 * Represents a wordgrid.
 */
public class WordGrid extends GridPane {

    // Number of rows and columns.
    public static final int N_ROWS = 6;
    public static final int N_COLUMNS = 5;

    // Tiles in this wordgrid.
    private WordGridTile[][] tiles;

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
        AnimationManager.initTileAnimations(tiles);
    }

}
