package org.volt4.wordle.animations;

import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.WordGridTile;

/**
 * Animates a tile bouncing.
 */
public class TileBounce implements WordleAnimation {

    // Duration of this animation.
    private static final long ANIMATION_DURATION = 900;

    // How high the tiles bounce.
    private static final double BOUNCE_HEIGHT = 30;

    // Tile this animates.
    private WordGridTile tile;

    /**
     * Creates the animation on the given tile.
     * @param tile Tile to animate.
     */
    public TileBounce(WordGridTile tile) {
        this.tile = tile;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        // Small delay letting the last cell flip.
        if (position < 0.4)
            return;
        position -= 0.4;
        position *= 1 / 0.4;
        position -= 0.25;
        if (position == 0)
            position = 0.0001;
        double yOffset = -BOUNCE_HEIGHT * Math.sin(4 * Math.PI * position) / (4 * Math.PI * position);
        tile.setTranslateY(yOffset);
    }

    @Override
    public void end() {
        tile.setTranslateY(0);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
