package org.volt4.wordle.animation;

import javafx.animation.AnimationTimer;
import org.volt4.wordle.Letters;
import org.volt4.wordle.controller.WordGridUIController;

import java.util.concurrent.TimeUnit;

/**
 * Small animation that plays when a cell in populated.
 */
@Deprecated
public class PopulateAnimation extends AnimationTimer {

    // How long the animation takes to complete.
    public static final long ANIMATION_DURATION = 80;

    // How big the cell grows.
    public static final double CELL_GROWTH = .07;

    // Cell this animates.
    private WordGridUIController.Cell cell;

    // Letter that gets populated.
    private Letters letter;

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
    public void runAnimation(Letters letter) {
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
            cell.getRoot().setScaleX(1);
            cell.getRoot().setScaleY(1);
            stop();
            return;
        }
        if (millisElapsed <= ANIMATION_DURATION / 2) {
            cell.getRoot().setScaleX(1 + (CELL_GROWTH * millisElapsed / (ANIMATION_DURATION / 2)));
            cell.getRoot().setScaleY(1 + (CELL_GROWTH * millisElapsed / (ANIMATION_DURATION / 2)));
        } else {
            if (!atLargest) {
                cell.setLetterInternal(letter);
                atLargest = true;
            }
            cell.getRoot().setScaleX(1 + (CELL_GROWTH * (ANIMATION_DURATION - millisElapsed) / (ANIMATION_DURATION / 2)));
            cell.getRoot().setScaleY(1 + (CELL_GROWTH * (ANIMATION_DURATION - millisElapsed) / (ANIMATION_DURATION / 2)));
        }
    }

}
