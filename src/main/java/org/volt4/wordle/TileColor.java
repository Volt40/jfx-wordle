package org.volt4.wordle;

/**
 * Enum containing all colors a tile (keyboard key or gid tile) can be.
 */
public enum TileColor {
    LIGHT_GREY, // Tile that has a letter that is not in the word.
    DARK_GREY, // Tile that has a letter that may or may not be in the word.
    YELLOW, // Tile that has a letter that is in the word, just in the wrong position.
    GREEN // Tile that contains a letter that is in the word, in the position.
}
