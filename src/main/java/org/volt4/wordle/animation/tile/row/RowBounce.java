package org.volt4.wordle.animation.tile.row;

import org.volt4.wordle.animation.AnimationManager;
import org.volt4.wordle.animation.WordleAnimation;

/**
 * Animates a row bouncing. (Winning animation)
 */
public class RowBounce implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 300;

    // Row of tiles this animates.
    private int row;

    // Used for animation.
    private boolean[] bounced;

    /**
     * Constructs the animation on the row of tiles.
     * @param row Row of tiles to animate.
     */
    public RowBounce(int row) {
        this.row = row;
        bounced = new boolean[5]; // Might change in future updates.
    }

    @Override
    public void start() {
        for (int i = 0; i < bounced.length; i++)
            bounced[i] = false;
    }

    @Override
    public void animate(double position) {
        int indexToBounce = (int) (position / (1d / 5d)); // Might change in future updates.
        if (!bounced[indexToBounce]) {
            bounced[indexToBounce] = true;
            AnimationManager.playTileBounceAnimation(row, indexToBounce);
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
