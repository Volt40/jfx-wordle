package org.volt4.wordle.animation;

import org.volt4.wordle.AnimationManager;
import org.volt4.wordle.Letter;
import org.volt4.wordle.TileColor;
import org.volt4.wordle.WordleAnimation;

/**
 * Animates a row getting revealed.
 */
public class RowReveal implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 1000;

    // Tiles this animates.
    private int row;

    // Colors to reveal too.
    private TileColor[] colors;

    // Letters to flip.
    private Letter[] letters;

    // Used for animation.
    private boolean[] flipped;

    /**
     * Constructs the animation on the given tiles.
     * @param row Tiles to animate.
     */
    public RowReveal(int row) {
        this.row = row;
    }

    /**
     * Sets the colors the row should flip to.
     * @param colors Colors to flip to.
     */
    public void setColors(TileColor[] colors) {
        this.colors = colors;
    }

    /**
     * Sets the letters to flip.
     * @param letters Letters to flip.
     */
    public void setLetters(Letter[] letters) {
        this.letters = letters;
    }

    @Override
    public void start() {
        flipped = new boolean[colors.length];
        for (int i = 0; i < flipped.length; i++)
            flipped[i] = false;
    }

    @Override
    public void animate(double position) {
        int indexToFlip = (int) (position * colors.length);
        if (!flipped[indexToFlip]) {
            flipped[indexToFlip] = true;
            AnimationManager.playTileFlipAnimation(row, indexToFlip, colors[indexToFlip], false);
            AnimationManager.playKeyFlipAnimation(letters[indexToFlip].ID(), colors[indexToFlip]);
        }
    }

    @Override
    public void end() {

    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
