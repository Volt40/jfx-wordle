package org.volt4.wordle.animation.tile.row;

import org.volt4.wordle.animation.WordleAnimation;
import org.volt4.wordle.controller.wordgrid.WordGridTile;

/**
 * Animates a row shaking.
 */
public class RowShake implements WordleAnimation {

    // Duration of this animation.
    public static final long ANIMATION_DURATION = 500;

    // Animation settings.
    private static final double OFFSET_MAG = 4;
    private static final double N_SHAKES = 3;

    // Row to shake.
    private WordGridTile[] tiles;

    /**
     * Constructs the animation on the row of tiles.
     * @param tiles Tiles to animate.
     */
    public RowShake(WordGridTile[] tiles) {
        this.tiles = tiles;
    }

    @Override
    public void start() {

    }

    @Override
    public void animate(double position) {
        double offset = OFFSET_MAG * Math.sin(N_SHAKES * Math.PI * 2 * position);
        setOffset(offset);
    }

    @Override
    public void end() {
        setOffset(0);
    }

    @Override
    public long getDuration() {
        return ANIMATION_DURATION;
    }

    /**
     * Sets the offset of all the tiles.
     * @param offset Offset to set.
     */
    private void setOffset(double offset) {
        for (WordGridTile tile : tiles)
            tile.setTranslateX(offset);
    }

}
