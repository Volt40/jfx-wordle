package org.volt4.wordle.animations;

import javafx.animation.AnimationTimer;
import org.volt4.wordle.WordGrid;

import java.util.concurrent.TimeUnit;

public class RevealAnimation extends AnimationTimer {

    // How long the animation takes to complete.
    public static final long ANIMATION_DURATION = 1000;

    // Row of cells that reveal.
    private WordGrid.Cell[] cellRow;

    // Colors to flip to.
    private FlipAnimation.Colors[] flipColors;

    // Time the animation starts.
    private long startTime;

    // Used for animation.
    private boolean[] flipped;

    /**
     * Constructs the animation to work with the row of cells.
     * @param cellRow Row of cells to animate.
     */
    public RevealAnimation(WordGrid.Cell[] cellRow) {
        this.cellRow = cellRow;
    }

    /**
     * Plays the animation.
     */
    public void playAnimation(FlipAnimation.Colors[] flipColors) {
        startTime = -1;
        this.flipColors = flipColors;
        flipped = new boolean[] {false, false, false, false, false};
        start();
    }

    @Override
    public void handle(long now) {
        if (startTime == -1)
            startTime = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS);
        long millisElapsed = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
        if (millisElapsed >= ANIMATION_DURATION) {
            stop();
            return;
        }
        int indexToFlip = (int) (millisElapsed / (ANIMATION_DURATION / 5d));
        if (!flipped[indexToFlip]) {
            flipped[indexToFlip] = true;
            cellRow[indexToFlip].flip(flipColors[indexToFlip]);
        }
    }

}