package org.volt4.wordle.animation;

import javafx.animation.AnimationTimer;
import org.volt4.wordle.TileColor;
import org.volt4.wordle.controller.WordGridUIController;
import org.volt4.wordle.WordleApplication;

import java.util.concurrent.TimeUnit;

/**
 * Animation for revealing a row of tiles.
 */
@Deprecated
public class RevealAnimation extends AnimationTimer {

    // How long the animation takes to complete.
    public static final long ANIMATION_DURATION = 1000;

    // Row of cells that reveal.
    private WordGridUIController.Cell[] cellRow;

    // Colors to flip to.
    private TileColor[] flipColors;

    // Time the animation starts.
    private long startTime;

    // Used for animation.
    private boolean[] flipped;

    /**
     * Constructs the animation to work with the row of cells.
     * @param cellRow Row of cells to animate.
     */
    public RevealAnimation(WordGridUIController.Cell[] cellRow) {
        this.cellRow = cellRow;
    }

    /**
     * Plays the animation.
     */
    public void playAnimation(TileColor[] flipColors) {
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
            boolean hasWon = true;
            for (TileColor color : flipColors)
                if (color != TileColor.GREEN)
                    hasWon = false;
            if (hasWon)
                WordleApplication.getController().embeddedGrid().playWinAnimation();
            stop();
            return;
        }
        int indexToFlip = (int) (millisElapsed / (ANIMATION_DURATION / 5d));
        if (!flipped[indexToFlip]) {
            flipped[indexToFlip] = true;
            cellRow[indexToFlip].flip(flipColors[indexToFlip]);
            WordleApplication.getController().getKeyboard().flipLetter(cellRow[indexToFlip].getLetterStr(), flipColors[indexToFlip]);
        }
    }

}