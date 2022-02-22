package org.volt4.wordle;

/**
 * Enum containing all colors a tile (keyboard key or gid tile) can be.
 */
public enum TileColor {

    LIGHT_GREY("grey-cells", "keyboard-tile-darkgrey"), // Tile that has a letter that is not in the word.
    DARK_GREY("empty-cells", "keyboard-tile-lightgrey"), // Tile that has a letter that may or may not be in the word.
    YELLOW("yellow-cells", "keyboard-tile-yellow"), // Tile that has a letter that is in the word, just in the wrong position.
    GREEN("green-cells", "keyboard-tile-green"); // Tile that contains a letter that is in the word, in the position.

    // CSS Style class names.
    private String tileStyleClassName;
    private String keyStyleClassName;

    /**
     * Creates the tile color and associates it the defined style classes.
     * @param tileStyleClassName Style class name for tiles.
     * @param keyStyleClassName Style class name for keys.
     */
    TileColor(String tileStyleClassName, String keyStyleClassName) {
        this.tileStyleClassName = tileStyleClassName;
        this.keyStyleClassName = keyStyleClassName;
    }

    /**
     * Gets the tile style class name.
     * @return The tile style class name.
     */
    public String getTileStyleClassName() {
        return tileStyleClassName;
    }

    /**
     * Gets the key style class name.
     * @return The key style class name.
     */
    public String getKeyStyleClassName() {
        return keyStyleClassName;
    }

}
