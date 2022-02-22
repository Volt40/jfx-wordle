package org.volt4.wordle.animations;

import org.volt4.wordle.Letter;
import org.volt4.wordle.WordleAnimation;
import org.volt4.wordle.controller.WordGridTile;

/**
 * Animates a tile getting populating.
 */
public class TilePopulate implements WordleAnimation {

    // Duration of this animation.
    private static final long ANIMATION_DURATION = 120;

    // How big the tiles grow.
    private static final double GROWTH = 0.07;

    // Tile this animates.
    private WordGridTile tile;

    // Parameters for animation.
    private Letter letter;
    private boolean atLargest;

    /**
     * Creates the animation on the given tile.
     * @param tile Tile to animate.
     */
    public TilePopulate(WordGridTile tile) {
        this.tile = tile;
    }

    /**
     * Sets the letter to populate.
     * @param letter Letter to populate.
     */
    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    @Override
    public void start() {
        atLargest = false;
    }

    @Override
    public void animate(double position) {
        if (!atLargest && position >= 0.5) {
            tile.setLetter(letter);
            atLargest = true;
        }
        double scale;
        if (position < 0.5)
            scale = 2 * position * GROWTH;
        else
            scale = 2 * (1 - position) * GROWTH;
        setScale(1 + scale);
        System.out.println(scale);
    }

    @Override
    public void end() {
        setScale(1);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }

    /**
     * Sets the scale of the tiles.
     * @param scale Scale to set.
     */
    private void setScale(double scale) {
        tile.setScaleX(scale);
        tile.setScaleY(scale);
    }

}
