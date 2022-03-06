package org.volt4.wordle.animation.tile.row;

import org.volt4.wordle.controller.config.SettingsScreen;
import org.volt4.wordle.type.Hint;
import org.volt4.wordle.animation.AnimationManager;
import org.volt4.wordle.type.Letter;
import org.volt4.wordle.animation.WordleAnimation;

/**
 * Animates a row getting revealed.
 */
public class RowReveal implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 1000;

    // Tiles this animates.
    private int row;

    // Colors to reveal too.
    private Hint[] colors;
    private Hint[] keyHints;

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
    public void setColors(Hint[] colors) {
        this.colors = colors;
    }

    /**
     * Sets the letters to flip.
     * @param letters Letters to flip.
     */
    public void setLetters(Letter[] letters) {
        this.letters = letters;
    }

    /**
     * Sets the key hints.
     * @param keyHints Key hints.
     */
    public void setKeyHints(Hint[] keyHints) {
        this.keyHints = keyHints;
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
            AnimationManager.playKeyFlipAnimation(letters[indexToFlip].ID(), keyHints[indexToFlip]);
        }
    }

    @Override
    public void end() {
        if (SettingsScreen.DisableAnimations) {
            for (int i = 0; i < colors.length; i++) {
                AnimationManager.playTileFlipAnimation(row, i, colors[i], false);
                AnimationManager.playKeyFlipAnimation(letters[i].ID(), keyHints[i]);
            }
        }
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }

}
