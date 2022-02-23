package org.volt4.wordle.archive.animation;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import org.volt4.wordle.Letter;
import org.volt4.wordle.WordleApplication;
import org.volt4.wordle.archive.WordGridUIController;

import java.util.concurrent.TimeUnit;

/**
 * Small animation that plays when a cell in populated.
 */
@Deprecated
public class PopulateAnimation extends AnimationTimer {

    // How long the animation takes to complete.
    public static final long ANIMATION_DURATION = 120;

    // How big the cell grows.
    public static final double CELL_GROWTH = .07;

    // Cell this animates.
    private WordGridUIController.Cell cell;

    // Letter that gets populated.
    private Letter letter;

    // Time the animation starts.
    private long startTime;

    // Used for animation.
    private boolean atLargest;

    /**
     * Constructs a PopulateAnimation object that animates the given cell.
     * @param cell Cell to animate.
     */
    public PopulateAnimation(WordGridUIController.Cell cell) {
        this.cell = cell;
    }

    /**
     * Runs the animation to populate the given latter.
     * @param letter Letter to populate.
     */
    public void runAnimation(Letter letter) {
        this.letter = letter;
        atLargest = false;
        startTime = -1;
        cell.getRoot().getStyleClass().clear();
        cell.getRoot().getStyleClass().add("filled-cells");
        start();
    }

    @Override
    public void handle(long now) {
        if (startTime == -1)
            startTime = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS);
        long millisElapsed = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
        if (millisElapsed >= ANIMATION_DURATION) {
            setScale(1);
            stop();
            return;
        }
        if (millisElapsed <= ANIMATION_DURATION / 2) {
            setScale(1 + (CELL_GROWTH * millisElapsed / (ANIMATION_DURATION / 2)));
        } else {
            if (!atLargest) {
                cell.setLetterInternal(letter);
                atLargest = true;
            }
            setScale(1 + (CELL_GROWTH * (ANIMATION_DURATION - millisElapsed) / (ANIMATION_DURATION / 2)));
        }
    }

    /**
     * Sets the scale to the panes.
     * @param scale Scale to set.
     */
    private void setScale(double scale) {
        cell.getRoot().setScaleX(scale);
        cell.getRoot().setScaleY(scale);
        AnchorPane keyboardLetterPane = WordleApplication.getController().getKeyboard().getEmbeddedPane(letter.getLetter());
        scale -= 1;
        scale *= 2;
        scale += 1;
        keyboardLetterPane.setScaleX(scale);
        keyboardLetterPane.setScaleY(scale);
    }

}
