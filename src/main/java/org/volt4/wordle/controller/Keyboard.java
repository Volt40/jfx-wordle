package org.volt4.wordle.controller;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.volt4.wordle.AnimationManager;
import org.volt4.wordle.Letter;

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

    // WordGrid this interacts with.
    private WordGrid wordGrid;

    /**
     * Constructs a keyboard.
     * @param wordGrid WordGrid this interacts with.
     */
    public Keyboard(WordGrid wordGrid) {
        this.wordGrid = wordGrid;
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
        for (int i = 0; i < keys.length; i++)
            for (int j = 0; j < keys[i].length; j++) {
                if (i == 1 && j == 0)
                    continue;
                keysList[keys[i][j].getLetter().ID()] = keys[i][j];
            }
        AnimationManager.initKeyAnimations(keysList);
    }

    /**
     * Ran when a key is clicked.
     * @param key Key to be clicked.
     */
    private void onClick(Letter key) {
        if (key == Letter.BACKSPACE)
            wordGrid.deleteLetter();
        else if (key == Letter.ENTER)
            wordGrid.enterWord();
        else
            wordGrid.inputLetter(key);
    }

}
