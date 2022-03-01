package org.volt4.wordle.controller;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.volt4.wordle.AnimationManager;
import org.volt4.wordle.Letter;
import org.volt4.wordle.TileColor;
import org.volt4.wordle.WordLists;

/**
 * Keyboard for wordle.
 */
public class Keyboard extends AnchorPane {

    // Conventional keyboard layout.
    private static final String[] KEYBOARD_LAYOUT = {"qwertyuiop", " asdfghjkl", " zxcvbnm "};

    // GridPane the keys are layed out on.
    private GridPane[] keyGrid;

    // All keys this keyboard contains.
    private KeyboardKey[][] keys;

    // Handles key presses.
    private KeyHandler handler;

    /**
     * Constructs a keyboard.
     */
    public Keyboard(KeyHandler handler) {
        this.handler = handler;
        // Layout the rows.
        keyGrid = new GridPane[3];
        keys = new KeyboardKey[3][];
        for (int i = 0; i < keys.length; i++)
            keys[i] = new KeyboardKey[KEYBOARD_LAYOUT[i].length()];
        for (int i = 0; i < keys.length; i++) {
            keyGrid[i] = new GridPane();
            keyGrid[i].setLayoutY(i * 40);
            for (int j = 0; j < keys[i].length; j++) {
                if (i == 1 && j == 0) {
                    AnchorPane spacer = new AnchorPane();
                    spacer.setPrefWidth(17.5);
                    keyGrid[1].add(spacer, j, i);
                    continue;
                }
                Letter letter = Letter.getMatch("" + KEYBOARD_LAYOUT[i].charAt(j));
                if (i == 2 && j == 0)
                    letter = Letter.ENTER;
                else if (i == 2 && j == KEYBOARD_LAYOUT[2].length() - 1)
                    letter = Letter.BACKSPACE;
                Letter finalLetter = letter; // Lambdas are weird.
                KeyboardKey key = new KeyboardKey(letter, () -> onClick(finalLetter));
                keys[i][j] = key;
                keyGrid[i].add(key, j, i);
            }
        }
        getChildren().addAll(keyGrid[0], keyGrid[1], keyGrid[2]);
        // Init the animations.
        KeyboardKey[] keysList = new KeyboardKey[Letter.values().length];
        double[] layoutYs = new double[Letter.values().length];
        for (int i = 0; i < keys.length; i++)
            for (int j = 0; j < keys[i].length; j++) {
                if (i == 1 && j == 0)
                    continue;
                keysList[keys[i][j].getLetter().ID()] = keys[i][j];
                layoutYs[keys[i][j].getLetter().ID()] = i * 40;
            }
        AnimationManager.initKeyAnimations(keysList, layoutYs, this);
        // Create clipping pane.
        Rectangle clip = new Rectangle(350, 170);
        clip.setLayoutX(getLayoutX());
        clip.setLayoutY(getLayoutY());
        setClip(clip);
    }

    /**
     * Resets the keyboard.
     */
    public void reset() {
        setAllKeysDisabled(false);
        for (int i = 3; i < Letter.values().length; i++)
            AnimationManager.playKeyFlipAnimation(i, TileColor.DARK_GREY);
    }

    /**
     * Updates the helpful keyboard.
     * @param word Current word guess
     * @param position Current tile.
     */
    public void updateHelpfulKeyboard(String word, int position) {
        for (int i = 0; i < keys.length; i++)
            for (int j = 0; j < keys[i].length; j++)
                if (i == 1 && j == 0 || i == 2 && j == 0 || i == 2 && j == 8)
                    continue;
                else
                    if (WordLists.isValidLetter(keys[i][j].getLetter(), position, word)) {
                        if (keys[i][j].isDisable())
                            AnimationManager.playKeyEnableAnimation(keys[i][j].getLetter().ID());
                    } else
                        if (!keys[i][j].isDisable())
                            AnimationManager.playKeyDisableAnimation(keys[i][j].getLetter().ID());
    }

    /**
     * Enables or disables all keys.
     * @param disable
     */
    public void setAllKeysDisabled(boolean disable) {
        for (int i = 0; i < keys.length; i++)
            for (int j = 0; j < keys[i].length; j++)
                if (i == 1 && j == 0 || i == 2 && j == 0 || i == 2 && j == 8)
                    continue;
                else {
                    if (disable && !keys[i][j].isDisable())
                        AnimationManager.playKeyDisableAnimation(keys[i][j].getLetter().ID());
                    else if (!disable && keys[i][j].isDisable())
                        AnimationManager.playKeyEnableAnimation(keys[i][j].getLetter().ID());
                }
    }

    /**
     * Ran when a key is clicked.
     * @param key Key to be clicked.
     */
    private void onClick(Letter key) {
        handler.onKeyPress(key);
    }

    /**
     * Handles key presses.
     */
    public interface KeyHandler {

        /**
         * Runs when a key is pressed.
         * @param key Key that is pressed.
         */
        void onKeyPress(Letter key);

    }

}
