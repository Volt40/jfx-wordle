package org.volt4.wordle.animation;

import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.WordGridTile;

/**
 * Animates a tile bouncing.
 */
public class TileBounce implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 400;

    // How high the tiles bounce.
    private static final double BOUNCE_HEIGHT = 30;

    // Tile this animates.
    private WordGridTile tile;

    // Used for animation.
    private boolean midBounce;

    /**
     * Creates the animation on the given tile.
     * @param tile Tile to animate.
     */
    public TileBounce(WordGridTile tile) {
        this.tile = tile;
    }

    @Override
    public void start() {
        midBounce = false;
        tile.toFront();
    }

    @Override
    public void animate(double position) {
        if (position >= 0.75 && !midBounce) {
            tile.getStyleClass().clear();
            tile.getStyleClass().add("correct-green-cells");
            midBounce = true;
        }
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
