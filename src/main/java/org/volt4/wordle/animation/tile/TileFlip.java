package org.volt4.wordle.animation.tile;

import org.volt4.wordle.type.Hint;
import org.volt4.wordle.type.Letter;
import org.volt4.wordle.animation.WordleAnimation;
import org.volt4.wordle.controller.wordgrid.WordGridTile;

/**
 * Animates a tile flipping.
 */
public class TileFlip implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 500;

    // Tile this animates.
    private WordGridTile tile;

    // Parameters for animation;
    private boolean isResetting;
    private boolean atMidpoint;
    private Hint colorToFlip;

    /**
     * Creates the animation on the given tile.
     * @param tile Tile to animate.
     */
    public TileFlip(WordGridTile tile) {
        this.tile = tile;
    }

    /**
     * Sets whether this is resetting or not.
     * @param isResetting Whether this is resetting or not.
     */
    public void isResetting(boolean isResetting) {
        this.isResetting = isResetting;
    }

    /**
     * Sets the color this flips to.
     * @param colorToFlip Color to flip to.
     */
    public void setColorToFlip(Hint colorToFlip) {
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
            if (isResetting)
                tile.setLetter(Letter.EMPTY);
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
        tile.getStyleClass().clear();
        tile.getStyleClass().add(colorToFlip.getTileStyleClassName());
        if (isResetting)
            tile.setLetter(Letter.EMPTY);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }

}
