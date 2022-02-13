package org.volt4.wordle.animations;

import javafx.animation.AnimationTimer;
import org.volt4.wordle.WordGrid;

import java.util.concurrent.TimeUnit;

/**
 * Animation for flipping the cells.
 */
public class FlipTileAnimation extends AnimationTimer {

    // How long the animation takes to complete.
    public static final long ANIMATION_DURATION = 500;

    // Cell this animates.
    private WordGrid.Cell cell;

    // Color this flips to.
    private Colors color;

    // Time this animation starts.
    private long startTime;

    // Used for animation.
    private boolean atMidpoint;

    /**
     * Constructs a FlipAnimation object.
     */
    public FlipTileAnimation(WordGrid.Cell cell) {
        this.cell = cell;
    }

    /**
     * Runs the animation to flip to the desired color.
     * @param color Color to flip to.
     */
    public void runAnimation(Colors color) {
        this.color = color;
        atMidpoint = false;
        startTime = -1;
        start();
    }

    @Override
    public void handle(long now) {
        if (startTime == -1)
            startTime = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS);
        long millisElapsed = TimeUnit.MILLISECONDS.convert(now, TimeUnit.NANOSECONDS) - startTime;
        if (millisElapsed >= ANIMATION_DURATION) {
            cell.getRoot().setRotate(0);
            stop();
            return;
        }
        if (millisElapsed <= ANIMATION_DURATION / 2) {
            cell.getRoot().setRotate(1 + (90 * millisElapsed / (ANIMATION_DURATION / 2)));
        } else {
            if (!atMidpoint) {
                cell.getRoot().getStyleClass().clear();
                switch(color) {
                    case GREY:
                        cell.getRoot().getStyleClass().add("grey-cells");
                        break;
                    case YELLOW:
                        cell.getRoot().getStyleClass().add("yellow-cells");
                        break;
                    case GREEN:
                        cell.getRoot().getStyleClass().add("green-cells");
                        break;
                }
                atMidpoint = true;
            }
            cell.getRoot().setRotate(1 + (90 * (ANIMATION_DURATION - millisElapsed) / (ANIMATION_DURATION / 2)));
        }
    }

    /**
     * Colors this can flip to.
     */
    public enum Colors {
        GREY, YELLOW, GREEN;
    }

}
