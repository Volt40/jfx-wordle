package org.volt4.wordle.animation;

import org.volt4.wordle.TileColor;
import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.WordGridTile;

/**
 * Animates a tile flipping.
 */
public class TileFlip implements WordleAnimation {

    // Duration of this animation.
    private static final long ANIMATION_DURATION = 500;

    // Tile this animates.
    private WordGridTile tile;

    // Parameters for animation;
    private boolean isReseting;
    private boolean atMidpoint;
    private TileColor colorToFlip;

    /**
     * Creates the animation on the given tile.
     * @param tile Tile to animate.
     */
    public TileFlip(WordGridTile tile) {
        this.tile = tile;
    }

    /**
     * Sets whether this is resetting or not.
     * @param isReseting Whether this is resetting or not.
     */
    public void isReseting(boolean isReseting) {
        this.isReseting = isReseting;
    }

    /**
     * Sets the color this flips to.
     * @param colorToFlip Color to flip to.
     */
    public void setColorToFlip(TileColor colorToFlip) {
        this.colorToFlip = colorToFlip;
    }

    @Override
    public void start() {
        atMidpoint = false;
    }

    @Override
    public void animate(double position) {
        if (!atMidpoint && position >= 0.5) {
            tile.getStyleClass().clear();
            tile.getStyleClass().add(colorToFlip.getTileStyleClassName());
            atMidpoint = true;
        }
        double angle;
        if (position < 0.5)
            angle = 180 * position;
        else
            angle = 180 * (1 - position);
        tile.setRotate(angle);
    }

    @Override
    public void end() {
        tile.setRotate(0);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }
}
