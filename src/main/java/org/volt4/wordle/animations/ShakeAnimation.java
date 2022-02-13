package org.volt4.wordle.animations;

import javafx.animation.AnimationTimer;
import org.volt4.wordle.WordGrid;

import java.util.concurrent.TimeUnit;

/**
 * Animation of a row of cells shaking.
 */
public class ShakeAnimation extends AnimationTimer {

    // How long the animation takes to complete.
    public static final long ANIMATION_DURATION = 500;

    // Animation settings.
    public static final double OFFSET_MAG = 4;
    public static final double N_SHAKES = 3;

    // Row of cells that shake.
    private WordGrid.Cell[] cellRow;

    // Time the animation starts.
    private long startTime;

    /**
     * Constructs the animation to work with the row of cells.
     * @param cellRow Row of cells to animate.
     */
    public ShakeAnimation(WordGrid.Cell[] cellRow) {
        this.cellRow = cellRow;
    }

    /**
     * Plays the animation.
     */
    public void playAnimation() {
        startTime = -1;
        start();
    }

    @Override
    public void handle(long now) {
        if (startTime == -1)
            startTime = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS);
        long millisElapsed = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
        if (millisElapsed >= ANIMATION_DURATION) {
            setOffset(0);
            stop();
            return;
        }
        double position = ((double) millisElapsed / (double) ANIMATION_DURATION);
        double offset = OFFSET_MAG * Math.sin(N_SHAKES * Math.PI * 2 * position);
        setOffset(offset);
    }

    /**
     * Sets x offset for the row.
     * @param offset Offset to be set.
     */
    private void setOffset(double offset) {
        for (WordGrid.Cell cell : cellRow)
            cell.getRoot().translateXProperty().set(offset);
    }
}
