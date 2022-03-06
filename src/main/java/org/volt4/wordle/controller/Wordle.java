package org.volt4.wordle.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.*;
import org.volt4.wordle.animation.AnimationManager;
import org.volt4.wordle.animation.tile.TileBounce;
import org.volt4.wordle.animation.tile.TileFlip;
import org.volt4.wordle.animation.tile.row.RowBounce;
import org.volt4.wordle.animation.tile.row.RowReveal;
import org.volt4.wordle.controller.component.LoseCard;
import org.volt4.wordle.controller.keyboard.Keyboard;
import org.volt4.wordle.controller.wordgrid.WordGrid;
import org.volt4.wordle.type.Hint;
import org.volt4.wordle.type.Letter;

import java.io.IOException;
import java.util.*;

/**
 * A Wordle Game.
 */
public class Wordle extends AnchorPane {

    // Number of rows and columns.
    public static int N_ROWS = 6;
    public static int N_COLUMNS = 5;

    @FXML
    private ImageView resetImage;

    @FXML
    private ImageView settingsImage;

    // WordGrid the game is played on.
    private WordGrid wordgrid;

    // Optional keyboard.
    private Keyboard keyboard;

    // Used for animation.
    private boolean keyboardHidden;
    private boolean settingsVisible;

    // Used for drag & drop.
    private double[] offset;

    // Current row and selected column.
    private int currentRow, selectedColumn;

    // Current answer.
    private String answer;

    // Won/Lose trackers.
    private boolean hasWon, hasLost;

    // Hard mode variables.
    private boolean[] lockedColumns;
    private Map<Letter, Integer> requiredLetters;

    /**
     * Constructs a Wordle game.
     */
    public Wordle() {
        // Load the layout.
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("layouts/Wordle.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentRow = 0;
        selectColumn(0);
        hasWon = false;
        hasLost = false;
        lockedColumns = new boolean[N_COLUMNS];
        requiredLetters = new HashMap<>();
        // Setup animations.
        keyboardHidden = true;
        settingsVisible = false;
        AnimationManager.initKeyboardAnimations();
        AnimationManager.initResetAnimations(resetImage);
        // Setup offsets.
        offset = new double[] {0, 0};
        // Construct keyboard and grid.
        wordgrid = new WordGrid(N_ROWS, N_COLUMNS);
        keyboard = new Keyboard(key -> onKeyboardKeyPressed(key));
        // Create lose card.
        LoseCard loseCard = new LoseCard();
        AnimationManager.initLoseCardAnimations(loseCard);
        // Create settings window.
        Settings settings = new Settings();
        settings.setLayoutY(30);
        settings.setLayoutX(350);
        AnimationManager.initSettingsAnimations(settings, settingsImage);
        // Set constraints.
        wordgrid.setLayoutY(30);
        keyboard.setLayoutY(450);
        // Add children.
        getChildren().addAll(wordgrid, keyboard, loseCard, settings);
        // Choose first answer.
        answer = WordLists.pickRandomAnswer();
    }

    /**
     * Resets the game.
     */
    public void reset() {
        wordgrid.reset();
        keyboard.reset();
        hasWon = false;
        if (hasLost)
            AnimationManager.playLoseCardHideAnimation();
        for (int i = 0; i < N_ROWS; i++)
            wordgrid.clearRowSelection(i);
        hasLost = false;
        currentRow = 0;
        selectedColumn = 0;
        chooseNewAnswer();
        if (Settings.HelpfulKeyboard)
            keyboard.setAllKeysDisabled(false);
    }

    /**
     * Runs when a key is inputted using the keyboard.
     * @param key Key that is pressed.
     */
    private void onKeyboardKeyPressed(Letter key) {
        if (key == Letter.ENTER)
            enterWord();
        else if (key == Letter.BACKSPACE)
            deleteLetter();
        else
            inputLetter(key);
    }

    /**
     * Inputs the letter into the grid.
     * @param letter Letter to be inputted.
     */
    public void inputLetter(Letter letter) {
        // Make sure the game is active
        if (hasWon || hasLost)
            return;
        if (selectedColumn == -1)
            return;
        wordgrid.setLetter(letter, currentRow, selectedColumn);
        if (selectedColumn == N_COLUMNS - 1)
            selectedColumn = -1;
        else
            selectColumn(selectedColumn + 1);
        refreshHelpfulKeyboard();
    }

    /**
     * Removed the last letter typed.
     */
    public void deleteLetter() {
        // Make sure the game is active
        if (hasWon || hasLost)
            return;
        int columnToDelete = selectedColumn == -1 ? N_COLUMNS - 1 : selectedColumn;
        while (columnToDelete != 0 && wordgrid.getLetter(currentRow, columnToDelete) == Letter.EMPTY)
            columnToDelete--;
        wordgrid.deleteLetter(currentRow, columnToDelete);
        selectColumn(columnToDelete);
        refreshHelpfulKeyboard();
    }

    /**
     * Attempts to enter the current word.
     */
    public void enterWord() {
        // Make sure the game is active
        if (hasWon || hasLost)
            return;
        // Get the word from the grid.
        String word = wordgrid.getWord(currentRow);
        // Check to make sure it is valid.
        // TODO: Hard mode implementation.
        if (!WordLists.guessIsValid(word)) {
            AnimationManager.playRowShakeAnimation(currentRow);
            return;
        }
        // Get the answer as a letter array.
        Letter[] answer = getAnswerAsArray();
        // Convert to guess to a letter array.
        Letter[] guess = new Letter[word.length()];
        for (int i = 0; i < guess.length; i++)
            guess[i] = Letter.getMatch("" + word.charAt(i));
        /*
          * At this point the word is valid and needs to be evaluated. This is done
          * according to the following criteria.
          * - If the letter is in the correct position, it is green.
          * - If the letter is in the word, but in the incorrect position, it is yellow.
          * - If the letter is in the word once, but appears twice in the guess, only
          *   the first instance will be colored (or the correct instance).
         */
        // Create hint arrays.
        Hint[] tileHints = new Hint[N_COLUMNS];
        Map<Letter, Hint> keyHints = new HashMap<>();
        // Keeps track of how many times a letter appears in the word.
        Map<Letter, Integer> letterOccurrencesGuess = new HashMap<>();
        Map<Letter, Integer> letterOccurrencesAnswer = new HashMap<>();
        // Build the maps.
        for (int i = 0; i < guess.length; i++) {
            // Check if the mapping to the guess letter already exists.
            if (letterOccurrencesGuess.containsKey(guess[i]))
                letterOccurrencesGuess.put(guess[i], letterOccurrencesGuess.get(guess[i]) + 1); // If it does, increment it.
            else
                letterOccurrencesGuess.put(guess[i], 1); // Otherwise, set it to 1.
            // Check is the mapping to the answer letter already exists.
            if (letterOccurrencesAnswer.containsKey(answer[i]))
                letterOccurrencesAnswer.put(answer[i], letterOccurrencesAnswer.get(answer[i]) + 1); // If it does, increment it.
            else
                letterOccurrencesAnswer.put(answer[i], 1); // Otherwise, set it to 1.
        }
        // For each letter in the word.
        for (int i = 0; i < word.length(); i++) {
            Letter letter = guess[i]; // Get the letter from the word.
            // Check to see if the letter is in the correct position.
            if (answer[i] == letter) {
                // If so update hints.
                tileHints[i] = Hint.GREEN;
                // Check to see if something went wrong.
                if (letterOccurrencesAnswer.get(letter) == 0) {
                    boolean firstHintRemoved = false;
                    boolean moreThanOneYellowHintsFound = false;
                    // If this is true, that means that there is a yellow instance of the letter earlier in the word.
                    for (int j = 0; j < i; j++)
                        if (guess[j] == letter && tileHints[j] == Hint.YELLOW) // Find a previous instance of the letter.
                            if (!firstHintRemoved) {
                                tileHints[j] = Hint.LIGHT_GREY; // Remove the yellow hint.
                                firstHintRemoved = true;
                            } else
                                moreThanOneYellowHintsFound = true;
                    if (!moreThanOneYellowHintsFound) // If only one other yellow hint was found, remove the key map reference.
                        keyHints.remove(letter);
                }
                // Make sure key hints does not have an overriding hint.
                if (keyHints.get(letter) == Hint.YELLOW)
                    // Set the hint to green and yellow.
                    keyHints.put(letter, Hint.YELLOW_GREEN);
                else // Otherwise, set to green as normal.
                    keyHints.put(letter, Hint.GREEN);
            // Check to see if the letter is in the word and has occurrences left.
            } else if (letterOccurrencesAnswer.containsKey(letter) && letterOccurrencesAnswer.get(letter) != 0) {
                // Update hints.
                tileHints[i] = Hint.YELLOW;
                // Make sure key hints does not have an overriding hint.
                if (keyHints.get(letter) == Hint.GREEN)
                    // Set the hint to yellow and green.
                    keyHints.put(letter, Hint.YELLOW_GREEN);
                else // Otherwise, set to yellow as normal.
                    keyHints.put(letter, Hint.YELLOW);
            } else {
                // At this point, the letter is not in the word, so set the hint to light grey.
                tileHints[i] = Hint.LIGHT_GREY;
                if (!keyHints.containsKey(letter))
                    keyHints.put(letter, Hint.LIGHT_GREY);
                continue;
            }
            // Decrement the occurrence.
            letterOccurrencesAnswer.put(letter, letterOccurrencesAnswer.get(letter) - 1);
        } // End for each letter loop.
        // Both hint collections should now be populated correctly.
        // TODO: Fix hard mode logic here.
        // Count the amount of hints for each letter.
        Map<Letter, Integer> letterHints = new HashMap<>();
        for (int i = 0; i < tileHints.length; i++)
            if (tileHints[i] == Hint.YELLOW || tileHints[i] == Hint.GREEN) // If a yellow/green hint is found.
                if (letterHints.containsKey(guess[i]))
                    letterHints.put(guess[i], letterHints.get(guess[i]) + 1); // If already in the map, increment 1.
                else
                    letterHints.put(guess[i], 1); // Otherwise, just set it to one.
        // Used in hard mode, update the locked array.
        for (int i = 0; i < tileHints.length; i++)
            if (tileHints[i] == Hint.GREEN) {
                lockedColumns[i] = true;
                letterHints.remove(guess[i]); // Remove one green hint.
            }
        // Merge
        // Construct the key hints sync list.
        Hint[] keyHintsSync = new Hint[guess.length];
        for (int i = 0; i < keyHintsSync.length; i++)
            keyHintsSync[i] = keyHints.get(guess[i]);
        // Play the reveal animation.
        AnimationManager.playRowRevealAnimation(currentRow, tileHints, keyHintsSync, guess);
        // Check if the game is won.
        if (Arrays.equals(guess, answer)) {
            // If the game is won, play winning animations.
            AnimationManager.playRowBounceAnimation(currentRow, RowReveal.ANIMATION_DURATION + TileFlip.ANIMATION_DURATION);
            AnimationManager.playRowDoubleFlipAnimation(currentRow, RowReveal.ANIMATION_DURATION + TileFlip.ANIMATION_DURATION + RowBounce.ANIMATION_DURATION + TileBounce.ANIMATION_DURATION);
            hasWon = true;
        // Check if the game has been lost.
        } else if (currentRow == N_ROWS - 1) {
            // Play lose card animation.
            AnimationManager.playLoseCardShowAnimation(this.answer, RowReveal.ANIMATION_DURATION + TileFlip.ANIMATION_DURATION);
            hasLost = true;
        }
        // Clear the row selections.
        wordgrid.clearRowSelection(currentRow);
        // Increment the current row.
        currentRow++;
        // Select the first column of the next row.
        selectColumn(0);
    }

    /**
     * If the helpful keyboard is active, this will refresh it.
     */
    public void refreshHelpfulKeyboard() {
        if (Settings.HelpfulKeyboard)
            keyboard.updateHelpfulKeyboard(wordgrid.getWord(currentRow), selectedColumn == -1 ? N_COLUMNS - 1 : selectedColumn);
    }

    /**
     * Clears the helpful keyboard.
     */
    public void clearHelpfulKeyboard() {
        keyboard.setAllKeysDisabled(false);
    }

    /**
     * Selects the given column.
     * @param column column to select.
     */
    private void selectColumn(int column) {
        selectedColumn = column;
        refreshHelpfulKeyboard();
    }

    /**
     * Returns the current answer as a letter array.
     * @return The current answer as a letter array.
     */
    private Letter[] getAnswerAsArray() {
        Letter[] answerAsArray = new Letter[answer.length()];
        for (int i = 0; i < answer.length(); i++)
            answerAsArray[i] = Letter.getMatch("" + answer.charAt(i));
        return answerAsArray;
    }

    /**
     * Picks a new random answer.
     */
    private void chooseNewAnswer() {
        answer = WordLists.pickRandomAnswer();
        // TODO: Linguist mode check.
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        double mX = event.getSceneX();
        double mY = event.getSceneY() - 30;
        if (mY <= 0)
            return;
        int row = (int) (mY / 70);
        int column = (int) (mX / 70);
        if (row == currentRow) {
            selectColumn(column);
            if (wordgrid != null)
                wordgrid.setSelected(currentRow, column);
        }
    }

    @FXML
    void onClose(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void onDragBarPressed(MouseEvent event) {
        offset[0] = event.getSceneX();
        offset[1] = event.getSceneY();
    }

    @FXML
    void onDragBarDragged(MouseEvent event) {
        WordleApplication.primaryStage.setX(event.getScreenX() - offset[0]);
        WordleApplication.primaryStage.setY(event.getScreenY() - offset[1]);
    }

    @FXML
    void onKeyboard(MouseEvent event) {
        if (keyboardHidden)
            AnimationManager.playKeyboardShowAnimation();
        else
            AnimationManager.playKeyboardHideAnimation();
        keyboardHidden = !keyboardHidden;
    }

    @FXML
    void onReset(MouseEvent event) {
        AnimationManager.playResetIconSpinAnimation();
        reset();
    }

    @FXML
    void onMinimize(MouseEvent event) {
        WordleApplication.primaryStage.setIconified(true);
    }

    @FXML
    void onSettings(MouseEvent event) {
        settingsVisible = !settingsVisible;
        if (settingsVisible) {
            AnimationManager.playSettingsShowAnimation();
            AnimationManager.playSpinSettingsIconAnimation();
        } else {
            AnimationManager.playSettingsHideAnimation();
            AnimationManager.stopSpinSettingsIconAnimation();
        }
    }

    /**
     * Consumers for the menubar.
     */
    @FXML void onXPressed(MouseEvent event) {event.consume();}
    @FXML void onXDragged(MouseEvent event) {event.consume();}
    @FXML void onResetPressed(MouseEvent event) {event.consume();}
    @FXML void onResetDragged(MouseEvent event) {event.consume();}
    @FXML void onKeyboardPressed(MouseEvent event) {event.consume();}
    @FXML void onKeyboardDragged(MouseEvent event) {event.consume();}
    @FXML void onMinimizePressed(MouseEvent event) {event.consume();}
    @FXML void onMinimizeDragged(MouseEvent event) {event.consume();}
    @FXML void onSettingsPressed(MouseEvent event) {event.consume();}
    @FXML void onSettingsDragged(MouseEvent event) {event.consume();}

}