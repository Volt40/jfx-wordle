package org.volt4.wordle;

/**
 * Themes for wordle. These include the following:
 * - High Contrast
 * - Low Contrast (default)
 * - Light
 * - Dark (default)
 */
public enum WordleTheme {

    HIGH_CONTRAST(2, "stylesheets/colors/highcontrast.css"),
    LOW_CONTRAST(2, "stylesheets/colors/lowcontrast.css"),
    LIGHT(1, "stylesheets/colors/light.css"),
    DARK(1, "stylesheets/colors/dark.css");

    // Path to the relevant css file.
    private String cssPath;

    // Stylesheet index.
    private int index;

    /**
     * Creates the theme.
     * @param index Stylesheet index.
     * @param cssPath Path to the stylesheet.
     */
    WordleTheme(int index, String cssPath) {
        this.cssPath = cssPath;
        this.index = index;
    }

    /**
     * Returns the stylesheet index.
     * @return The stylesheet index.
     */
    public int getStylesheetIndex() {
        return index;
    }

    /**
     * Returns the path to the css file.
     * @return The path to the css file.
     */
    public String pathToCss() {
        return cssPath;
    }

}
